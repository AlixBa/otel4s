/*
 * Copyright 2023 Typelevel
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
package experimental.attributes

// DO NOT EDIT, this is an Auto-generated file from buildscripts/templates/registry/otel4s/attributes/SemanticAttributes.scala.j2
object UrlExperimentalAttributes {

  /** Domain extracted from the `url.full`, such as "opentelemetry.io".
    *
    * @note
    *   <p> In some cases a URL may refer to an IP and/or port directly, without a domain name. In this case, the IP
    *   address would go to the domain field. If the URL contains a <a
    *   href="https://www.rfc-editor.org/rfc/rfc2732#section-2">literal IPv6 address</a> enclosed by `[` and `]`, the
    *   `[` and `]` characters should also be captured in the domain field.
    */
  val UrlDomain: AttributeKey[String] =
    AttributeKey("url.domain")

  /** The file extension extracted from the `url.full`, excluding the leading dot.
    *
    * @note
    *   <p> The file extension is only set if it exists, as not every url has a file extension. When the file name has
    *   multiple extensions `example.tar.gz`, only the last one should be captured `gz`, not `tar.gz`.
    */
  val UrlExtension: AttributeKey[String] =
    AttributeKey("url.extension")

  /** The <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.5">URI fragment</a> component
    */
  @deprecated(
    "use `org.typelevel.otel4s.semconv.attributes.UrlAttributes.UrlFragment` instead.",
    ""
  )
  val UrlFragment: AttributeKey[String] =
    AttributeKey("url.fragment")

  /** Absolute URL describing a network resource according to <a
    * href="https://www.rfc-editor.org/rfc/rfc3986">RFC3986</a>
    *
    * @note
    *   <p> For network calls, URL usually has `scheme://host[:port][path][?query][#fragment]` format, where the
    *   fragment is not transmitted over HTTP, but if it is known, it SHOULD be included nevertheless. <p> `url.full`
    *   MUST NOT contain credentials passed via URL in form of `https://username:password@www.example.com/`. In such
    *   case username and password SHOULD be redacted and attribute's value SHOULD be
    *   `https://REDACTED:REDACTED@www.example.com/`. <p> `url.full` SHOULD capture the absolute URL when it is
    *   available (or can be reconstructed). <p> Sensitive content provided in `url.full` SHOULD be scrubbed when
    *   instrumentations can identify it. <p>
    *
    * Query string values for the following keys SHOULD be redacted by default and replaced by the value `REDACTED`:
    * <ul> <li><a
    * href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/RESTAuthentication.html#RESTAuthenticationQueryStringAuth">`AWSAccessKeyId`</a>
    * <li><a
    * href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/RESTAuthentication.html#RESTAuthenticationQueryStringAuth">`Signature`</a>
    * <li><a href="https://learn.microsoft.com/azure/storage/common/storage-sas-overview#sas-token">`sig`</a> <li><a
    * href="https://cloud.google.com/storage/docs/access-control/signed-urls">`X-Goog-Signature`</a> </ul> <p> This list
    * is subject to change over time. <p> When a query string value is redacted, the query string key SHOULD still be
    * preserved, e.g. `https://www.example.com/path?color=blue&sig=REDACTED`.
    */
  @deprecated(
    "use `org.typelevel.otel4s.semconv.attributes.UrlAttributes.UrlFull` instead.",
    ""
  )
  val UrlFull: AttributeKey[String] =
    AttributeKey("url.full")

  /** Unmodified original URL as seen in the event source.
    *
    * @note
    *   <p> In network monitoring, the observed URL may be a full URL, whereas in access logs, the URL is often just
    *   represented as a path. This field is meant to represent the URL as it was observed, complete or not.
    *   `url.original` might contain credentials passed via URL in form of `https://username:password@www.example.com/`.
    *   In such case password and username SHOULD NOT be redacted and attribute's value SHOULD remain the same.
    */
  val UrlOriginal: AttributeKey[String] =
    AttributeKey("url.original")

  /** The <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.3">URI path</a> component
    *
    * @note
    *   <p> Sensitive content provided in `url.path` SHOULD be scrubbed when instrumentations can identify it.
    */
  @deprecated(
    "use `org.typelevel.otel4s.semconv.attributes.UrlAttributes.UrlPath` instead.",
    ""
  )
  val UrlPath: AttributeKey[String] =
    AttributeKey("url.path")

  /** Port extracted from the `url.full`
    */
  val UrlPort: AttributeKey[Long] =
    AttributeKey("url.port")

  /** The <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.4">URI query</a> component
    *
    * @note
    *   <p> Sensitive content provided in `url.query` SHOULD be scrubbed when instrumentations can identify it. <p>
    *
    * Query string values for the following keys SHOULD be redacted by default and replaced by the value `REDACTED`:
    * <ul> <li><a
    * href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/RESTAuthentication.html#RESTAuthenticationQueryStringAuth">`AWSAccessKeyId`</a>
    * <li><a
    * href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/RESTAuthentication.html#RESTAuthenticationQueryStringAuth">`Signature`</a>
    * <li><a href="https://learn.microsoft.com/azure/storage/common/storage-sas-overview#sas-token">`sig`</a> <li><a
    * href="https://cloud.google.com/storage/docs/access-control/signed-urls">`X-Goog-Signature`</a> </ul> <p> This list
    * is subject to change over time. <p> When a query string value is redacted, the query string key SHOULD still be
    * preserved, e.g. `q=OpenTelemetry&sig=REDACTED`.
    */
  @deprecated(
    "use `org.typelevel.otel4s.semconv.attributes.UrlAttributes.UrlQuery` instead.",
    ""
  )
  val UrlQuery: AttributeKey[String] =
    AttributeKey("url.query")

  /** The highest registered url domain, stripped of the subdomain.
    *
    * @note
    *   <p> This value can be determined precisely with the <a href="https://publicsuffix.org/">public suffix list</a>.
    *   For example, the registered domain for `foo.example.com` is `example.com`. Trying to approximate this by simply
    *   taking the last two labels will not work well for TLDs such as `co.uk`.
    */
  val UrlRegisteredDomain: AttributeKey[String] =
    AttributeKey("url.registered_domain")

  /** The <a href="https://www.rfc-editor.org/rfc/rfc3986#section-3.1">URI scheme</a> component identifying the used
    * protocol.
    */
  @deprecated(
    "use `org.typelevel.otel4s.semconv.attributes.UrlAttributes.UrlScheme` instead.",
    ""
  )
  val UrlScheme: AttributeKey[String] =
    AttributeKey("url.scheme")

  /** The subdomain portion of a fully qualified domain name includes all of the names except the host name under the
    * registered_domain. In a partially qualified domain, or if the qualification level of the full name cannot be
    * determined, subdomain contains all of the names below the registered domain.
    *
    * @note
    *   <p> The subdomain portion of `www.east.mydomain.co.uk` is `east`. If the domain has multiple levels of
    *   subdomain, such as `sub2.sub1.example.com`, the subdomain field should contain `sub2.sub1`, with no trailing
    *   period.
    */
  val UrlSubdomain: AttributeKey[String] =
    AttributeKey("url.subdomain")

  /** The low-cardinality template of an <a href="https://www.rfc-editor.org/rfc/rfc3986#section-4.2">absolute path
    * reference</a>.
    */
  val UrlTemplate: AttributeKey[String] =
    AttributeKey("url.template")

  /** The effective top level domain (eTLD), also known as the domain suffix, is the last part of the domain name. For
    * example, the top level domain for example.com is `com`.
    *
    * @note
    *   <p> This value can be determined precisely with the <a href="https://publicsuffix.org/">public suffix list</a>.
    */
  val UrlTopLevelDomain: AttributeKey[String] =
    AttributeKey("url.top_level_domain")

}
