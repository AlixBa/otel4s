# Testkit

The `otel4s-oteljava-testkit` provides in-memory implementations of metric and trace exporters.
In-memory data can be used to test the structure of the spans, the names of instruments, and many more.

The testkit is framework-agnostic, so it can be used with any test framework, such as weaver, munit, scalatest.

## Getting started

@:select(build-tool)

@:choice(sbt)

Add settings to the `build.sbt`:

```scala
libraryDependencies ++= Seq(
  "org.typelevel" %% "otel4s-oteljava-testkit" % "@VERSION@" % Test, // <1>
)
```

@:choice(scala-cli)

Add directives to the `*.scala` file:

```scala
//> using test.dep "org.typelevel::otel4s-oteljava-testkit:@VERSION@" // <1>
```

@:@

1. Add the `otel4s-oteljava-testkit` library

## Testing metrics

Let's assume we have a program that increments a counter by one and sets the gauge's value to 42.
Here is how we can test this program:

```scala mdoc:silent:reset
import cats.effect.IO
import org.typelevel.otel4s.metrics.MeterProvider
import org.typelevel.otel4s.oteljava.testkit.OtelJavaTestkit
import org.typelevel.otel4s.oteljava.testkit.metrics.data.{Metric, MetricData}

// the program that we want to test 
def program(meterProvider: MeterProvider[IO]): IO[Unit] =
  for {
    meter <- meterProvider.get("service")
    
    counter <- meter.counter[Long]("service.counter").create
    _ <- counter.inc()

    gauge <- meter.gauge[Long]("service.gauge").create
    _ <- gauge.record(42L)
  } yield ()

// the test
def test: IO[Unit] = 
  OtelJavaTestkit.inMemory[IO]().use { testkit =>
    // the list of expected metrics
    val expected = List(
      TelemetryMetric.SumLong("service.counter", List(1L)),
      TelemetryMetric.GaugeLong("service.gauge", List(42L))
    )
    
    for {
      // invoke the program
      _ <- program(testkit.meterProvider)
      // collect the metrics
      metrics <- testkit.collectMetrics[Metric]
      // verify the collected metrics
      _ <- assertMetrics(metrics, expected)
    } yield ()
  }
  
// here you can use an assertion mechanism from your favorite testing framework
def assertMetrics(metrics: List[Metric], expected: List[TelemetryMetric]): IO[Unit] =
  IO {
    assert(metrics.sortBy(_.name).map(TelemetryMetric.fromMetric) == expected)
  }
  
// a minimized representation of the MetricData to simplify testing
sealed trait TelemetryMetric
object TelemetryMetric {
  case class SumLong(name: String, values: List[Long]) extends TelemetryMetric
  case class SumDouble(name: String, values: List[Double]) extends TelemetryMetric

  case class GaugeLong(name: String, values: List[Long]) extends TelemetryMetric
  case class GaugeDouble(name: String, values: List[Double]) extends TelemetryMetric

  case class Summary(name: String, values: List[Double]) extends TelemetryMetric
  case class Histogram(name: String, values: List[Double]) extends TelemetryMetric
  case class ExponentialHistogram(name: String, values: List[Double]) extends TelemetryMetric

  def fromMetric(metric: Metric): TelemetryMetric =
    metric.data match {
      case MetricData.LongGauge(points)   => GaugeLong(metric.name, points.map(_.value))
      case MetricData.DoubleGauge(points) => GaugeDouble(metric.name, points.map(_.value))
      case MetricData.LongSum(points)     => SumLong(metric.name, points.map(_.value))
      case MetricData.DoubleSum(points)   => SumDouble(metric.name, points.map(_.value))
      case MetricData.Summary(points)     => Summary(metric.name, points.map(_.value.sum))
      case MetricData.Histogram(points)   => Histogram(metric.name, points.map(_.value.sum))
      case MetricData.ExponentialHistogram(points) =>
        ExponentialHistogram(metric.name, points.map(_.value.sum))
    }
}
```

`MetricData` provides **all** information about the metric:
name, instrumentation scope, telemetry resource, data points,
associated attributes, collection time window, and so on.

It's hard to implement an assertion that verifies **all** aspects of the metric
because many things must be considered, such as time window, attributes, exemplars, etc.
To simplify the testing process, we can define a minimized projection of `MetricData` such as `TelemetryMetric`.

```scala mdoc:invisible
// we silently run the test to ensure it's actually correct
import cats.effect.unsafe.implicits.global
test.unsafeRunSync()
```

## Testing spans

Let's assume we want to test the structure of created spans:

```scala mdoc:reset
import cats.effect.IO
import io.opentelemetry.sdk.trace.data.SpanData
import org.typelevel.otel4s.oteljava.testkit.OtelJavaTestkit
import org.typelevel.otel4s.trace.TracerProvider
import scala.concurrent.duration._

// the program that we want to test 
def program(tracerProvider: TracerProvider[IO]): IO[Unit] =
  for {
    tracer <- tracerProvider.get("service")
    _ <- tracer.span("app.span").surround {
      tracer.span("app.nested.1").surround(IO.sleep(200.millis)) >>
      tracer.span("app.nested.2").surround(IO.sleep(300.millis))
    }
  } yield ()

// the test
def test: IO[Unit] =
  OtelJavaTestkit.inMemory[IO]().use { testkit =>
    // the list of expected spans
    val expected = List(
      SpanTree(
        TelemetrySpan("app.span"),
        List(
          SpanTree(TelemetrySpan("app.nested.1"), Nil),
          SpanTree(TelemetrySpan("app.nested.2"), Nil)
        )
      )
    )

    for {
      // invoke the program
      _ <- program(testkit.tracerProvider)
      // collect the finished spans
      spans <- testkit.finishedSpans
      // verify the collected spans
      _ <- assertSpans(spans, expected)
    } yield ()
  }

// here you can use an assertion mechanism from your favorite testing framework
def assertSpans(spans: List[SpanData], expected: List[SpanTree[TelemetrySpan]]): IO[Unit] =
  IO {
    val trees = SpanTree.fromSpans(spans)
    assert(trees.map(_.map(data => TelemetrySpan(data.getName))) == expected)
  }

// a minimized representation of the SpanData to simplify testing
case class TelemetrySpan(name: String)

// a tree-like representation of the spans
case class SpanTree[A](current: A, children: List[SpanTree[A]]) {
  def map[B](f: A => B): SpanTree[B] = SpanTree(f(current), children.map(_.map(f)))
}
object SpanTree {
  def fromSpans(spans: List[SpanData]): List[SpanTree[SpanData]] = {
    val byParent = spans.groupBy { s =>
      Option.when(s.getParentSpanContext.isValid)(s.getParentSpanId)
    }
    val topNodes = byParent.getOrElse(None, Nil)
    val bottomToTop = sortNodesByDepth(0, topNodes, byParent, Nil)
    val maxDepth = bottomToTop.headOption.map(_.depth).getOrElse(0)
    buildFromBottom(maxDepth, bottomToTop, byParent, Map.empty)
  }
  
  private case class EntryWithDepth(data: SpanData, depth: Int)
  
  @annotation.tailrec
  private def sortNodesByDepth(
      depth: Int,
      nodesInDepth: List[SpanData],
      nodesByParent: Map[Option[String], List[SpanData]],
      acc: List[EntryWithDepth]
  ): List[EntryWithDepth] = {
    val withDepth = nodesInDepth.map(n => EntryWithDepth(n, depth))
    val calculated = withDepth ++ acc
    val children = nodesInDepth.flatMap { n =>
      nodesByParent.getOrElse(Some(n.getSpanId), Nil)
    }
    children match {
      case Nil =>
        calculated
      case _ =>
        sortNodesByDepth(depth + 1, children, nodesByParent, calculated)
    }
  }
  
  @annotation.tailrec
  private def buildFromBottom(
      depth: Int,
      remaining: List[EntryWithDepth],
      nodesByParent: Map[Option[String], List[SpanData]],
      processedNodesById: Map[String, SpanTree[SpanData]]
  ): List[SpanTree[SpanData]] = {
    val (nodesOnCurrentDepth, rest) = remaining.span(_.depth == depth)
    val newProcessedNodes = nodesOnCurrentDepth.map { n =>
      val nodeId = n.data.getSpanId
      val children = nodesByParent
        .getOrElse(Some(nodeId), Nil)
        .flatMap(c => processedNodesById.get(c.getSpanId))
      val leaf = SpanTree(n.data, children)
      nodeId -> leaf
    }.toMap
    if (depth > 0) {
      buildFromBottom(
        depth - 1, 
        rest, 
        nodesByParent, 
        processedNodesById ++ newProcessedNodes
      )
    } else {
      // top nodes
      newProcessedNodes.values.toList
    }
  }
}
```

`SpanData` provides **all** information about the span:
name, instrumentation scope, telemetry resource, associated attributes, time window, and so on.

It's difficult to implement an assertion that verifies **all** aspects of the span
because many things must be considered, such as time windows, attributes, etc.
To simplify the testing process, we can define a minimized projection of `SpanData`, such as `TelemetrySpan`.

```scala mdoc:invisible
// we silently run the test to ensure it's actually correct
import cats.effect.unsafe.implicits.global
test.unsafeRunSync()
```