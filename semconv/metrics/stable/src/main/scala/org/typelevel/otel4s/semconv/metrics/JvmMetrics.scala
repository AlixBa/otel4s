/*
 * Copyright 2024 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.typelevel.otel4s
package semconv
package metrics

import org.typelevel.otel4s.metrics._
import org.typelevel.otel4s.semconv.attributes._

// DO NOT EDIT, this is an Auto-generated file from buildscripts/templates/registry/otel4s/metrics/SemanticMetrics.scala.j2
object JvmMetrics {

  val specs: List[MetricSpec] = List(
    ClassCount,
    ClassLoaded,
    ClassUnloaded,
    CpuCount,
    CpuRecentUtilization,
    CpuTime,
    GcDuration,
    MemoryCommitted,
    MemoryLimit,
    MemoryUsed,
    MemoryUsedAfterLastGc,
    ThreadCount,
  )

  /** Number of classes currently loaded.
    */
  object ClassCount extends MetricSpec {

    val name: String = "jvm.class.count"
    val description: String = "Number of classes currently loaded."
    val unit: String = "{class}"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = Nil

    def create[F[_]: Meter]: F[UpDownCounter[F, Long]] =
      Meter[F]
        .upDownCounter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Number of classes loaded since JVM start.
    */
  object ClassLoaded extends MetricSpec {

    val name: String = "jvm.class.loaded"
    val description: String = "Number of classes loaded since JVM start."
    val unit: String = "{class}"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = Nil

    def create[F[_]: Meter]: F[Counter[F, Long]] =
      Meter[F]
        .counter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Number of classes unloaded since JVM start.
    */
  object ClassUnloaded extends MetricSpec {

    val name: String = "jvm.class.unloaded"
    val description: String = "Number of classes unloaded since JVM start."
    val unit: String = "{class}"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = Nil

    def create[F[_]: Meter]: F[Counter[F, Long]] =
      Meter[F]
        .counter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Number of processors available to the Java virtual machine.
    */
  object CpuCount extends MetricSpec {

    val name: String = "jvm.cpu.count"
    val description: String = "Number of processors available to the Java virtual machine."
    val unit: String = "{cpu}"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = Nil

    def create[F[_]: Meter]: F[UpDownCounter[F, Long]] =
      Meter[F]
        .upDownCounter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Recent CPU utilization for the process as reported by the JVM. <p>
    * @note
    *   <p> The value range is [0.0,1.0]. This utilization is not defined as being for the specific interval since last
    *   measurement (unlike `system.cpu.utilization`). <a
    *   href="https://docs.oracle.com/en/java/javase/17/docs/api/jdk.management/com/sun/management/OperatingSystemMXBean.html#getProcessCpuLoad()">Reference</a>.
    */
  object CpuRecentUtilization extends MetricSpec {

    val name: String = "jvm.cpu.recent_utilization"
    val description: String = "Recent CPU utilization for the process as reported by the JVM."
    val unit: String = "1"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = Nil

    def create[F[_]: Meter]: F[Gauge[F, Long]] =
      Meter[F]
        .gauge[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** CPU time used by the process as reported by the JVM.
    */
  object CpuTime extends MetricSpec {

    val name: String = "jvm.cpu.time"
    val description: String = "CPU time used by the process as reported by the JVM."
    val unit: String = "s"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = Nil

    def create[F[_]: Meter]: F[Counter[F, Long]] =
      Meter[F]
        .counter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Duration of JVM garbage collection actions.
    */
  object GcDuration extends MetricSpec {

    val name: String = "jvm.gc.duration"
    val description: String = "Duration of JVM garbage collection actions."
    val unit: String = "s"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Name of the garbage collector action. <p>
        * @note
        *   <p> Garbage collector action is generally obtained via <a
        *   href="https://docs.oracle.com/en/java/javase/11/docs/api/jdk.management/com/sun/management/GarbageCollectionNotificationInfo.html#getGcAction()">GarbageCollectionNotificationInfo#getGcAction()</a>.
        */
      val jvmGcAction: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmGcAction,
          List(
            "end of minor GC",
            "end of major GC",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** Name of the garbage collector. <p>
        * @note
        *   <p> Garbage collector name is generally obtained via <a
        *   href="https://docs.oracle.com/en/java/javase/11/docs/api/jdk.management/com/sun/management/GarbageCollectionNotificationInfo.html#getGcName()">GarbageCollectionNotificationInfo#getGcName()</a>.
        */
      val jvmGcName: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmGcName,
          List(
            "G1 Young Generation",
            "G1 Old Generation",
          ),
          Requirement.recommended,
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          jvmGcAction,
          jvmGcName,
        )
    }

    def create[F[_]: Meter](boundaries: BucketBoundaries): F[Histogram[F, Double]] =
      Meter[F]
        .histogram[Double](name)
        .withDescription(description)
        .withUnit(unit)
        .withExplicitBucketBoundaries(boundaries)
        .create

  }

  /** Measure of memory committed.
    */
  object MemoryCommitted extends MetricSpec {

    val name: String = "jvm.memory.committed"
    val description: String = "Measure of memory committed."
    val unit: String = "By"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Name of the memory pool. <p>
        * @note
        *   <p> Pool names are generally obtained via <a
        *   href="https://docs.oracle.com/en/java/javase/11/docs/api/java.management/java/lang/management/MemoryPoolMXBean.html#getName()">MemoryPoolMXBean#getName()</a>.
        */
      val jvmMemoryPoolName: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryPoolName,
          List(
            "G1 Old Gen",
            "G1 Eden space",
            "G1 Survivor Space",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** The type of memory.
        */
      val jvmMemoryType: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryType,
          List(
            "heap",
            "non_heap",
          ),
          Requirement.recommended,
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          jvmMemoryPoolName,
          jvmMemoryType,
        )
    }

    def create[F[_]: Meter]: F[UpDownCounter[F, Long]] =
      Meter[F]
        .upDownCounter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Measure of max obtainable memory.
    */
  object MemoryLimit extends MetricSpec {

    val name: String = "jvm.memory.limit"
    val description: String = "Measure of max obtainable memory."
    val unit: String = "By"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Name of the memory pool. <p>
        * @note
        *   <p> Pool names are generally obtained via <a
        *   href="https://docs.oracle.com/en/java/javase/11/docs/api/java.management/java/lang/management/MemoryPoolMXBean.html#getName()">MemoryPoolMXBean#getName()</a>.
        */
      val jvmMemoryPoolName: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryPoolName,
          List(
            "G1 Old Gen",
            "G1 Eden space",
            "G1 Survivor Space",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** The type of memory.
        */
      val jvmMemoryType: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryType,
          List(
            "heap",
            "non_heap",
          ),
          Requirement.recommended,
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          jvmMemoryPoolName,
          jvmMemoryType,
        )
    }

    def create[F[_]: Meter]: F[UpDownCounter[F, Long]] =
      Meter[F]
        .upDownCounter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Measure of memory used.
    */
  object MemoryUsed extends MetricSpec {

    val name: String = "jvm.memory.used"
    val description: String = "Measure of memory used."
    val unit: String = "By"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Name of the memory pool. <p>
        * @note
        *   <p> Pool names are generally obtained via <a
        *   href="https://docs.oracle.com/en/java/javase/11/docs/api/java.management/java/lang/management/MemoryPoolMXBean.html#getName()">MemoryPoolMXBean#getName()</a>.
        */
      val jvmMemoryPoolName: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryPoolName,
          List(
            "G1 Old Gen",
            "G1 Eden space",
            "G1 Survivor Space",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** The type of memory.
        */
      val jvmMemoryType: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryType,
          List(
            "heap",
            "non_heap",
          ),
          Requirement.recommended,
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          jvmMemoryPoolName,
          jvmMemoryType,
        )
    }

    def create[F[_]: Meter]: F[UpDownCounter[F, Long]] =
      Meter[F]
        .upDownCounter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Measure of memory used, as measured after the most recent garbage collection event on this pool.
    */
  object MemoryUsedAfterLastGc extends MetricSpec {

    val name: String = "jvm.memory.used_after_last_gc"
    val description: String =
      "Measure of memory used, as measured after the most recent garbage collection event on this pool."
    val unit: String = "By"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Name of the memory pool. <p>
        * @note
        *   <p> Pool names are generally obtained via <a
        *   href="https://docs.oracle.com/en/java/javase/11/docs/api/java.management/java/lang/management/MemoryPoolMXBean.html#getName()">MemoryPoolMXBean#getName()</a>.
        */
      val jvmMemoryPoolName: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryPoolName,
          List(
            "G1 Old Gen",
            "G1 Eden space",
            "G1 Survivor Space",
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** The type of memory.
        */
      val jvmMemoryType: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmMemoryType,
          List(
            "heap",
            "non_heap",
          ),
          Requirement.recommended,
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          jvmMemoryPoolName,
          jvmMemoryType,
        )
    }

    def create[F[_]: Meter]: F[UpDownCounter[F, Long]] =
      Meter[F]
        .upDownCounter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

  /** Number of executing platform threads.
    */
  object ThreadCount extends MetricSpec {

    val name: String = "jvm.thread.count"
    val description: String = "Number of executing platform threads."
    val unit: String = "{thread}"
    val stability: Stability = Stability.stable
    val attributeSpecs: List[AttributeSpec[_]] = AttributeSpecs.specs

    object AttributeSpecs {

      /** Whether the thread is daemon or not.
        */
      val jvmThreadDaemon: AttributeSpec[Boolean] =
        AttributeSpec(
          JvmAttributes.JvmThreadDaemon,
          List(
          ),
          Requirement.recommended,
          Stability.stable
        )

      /** State of the thread.
        */
      val jvmThreadState: AttributeSpec[String] =
        AttributeSpec(
          JvmAttributes.JvmThreadState,
          List(
            "runnable",
            "blocked",
          ),
          Requirement.recommended,
          Stability.stable
        )

      val specs: List[AttributeSpec[_]] =
        List(
          jvmThreadDaemon,
          jvmThreadState,
        )
    }

    def create[F[_]: Meter]: F[UpDownCounter[F, Long]] =
      Meter[F]
        .upDownCounter[Long](name)
        .withDescription(description)
        .withUnit(unit)
        .create

  }

}
