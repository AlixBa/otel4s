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
object SecurityRuleExperimentalAttributes {

  /** A categorization value keyword used by the entity using the rule for detection of this event
    */
  val SecurityRuleCategory: AttributeKey[String] =
    AttributeKey("security_rule.category")

  /** The description of the rule generating the event.
    */
  val SecurityRuleDescription: AttributeKey[String] =
    AttributeKey("security_rule.description")

  /** Name of the license under which the rule used to generate this event is made available.
    */
  val SecurityRuleLicense: AttributeKey[String] =
    AttributeKey("security_rule.license")

  /** The name of the rule or signature generating the event.
    */
  val SecurityRuleName: AttributeKey[String] =
    AttributeKey("security_rule.name")

  /** Reference URL to additional information about the rule used to generate this event.
    *
    * @note
    *   <p> The URL can point to the vendor’s documentation about the rule. If that’s not available, it can also be a
    *   link to a more general page describing this type of alert.
    */
  val SecurityRuleReference: AttributeKey[String] =
    AttributeKey("security_rule.reference")

  /** Name of the ruleset, policy, group, or parent category in which the rule used to generate this event is a member.
    */
  val SecurityRuleRulesetName: AttributeKey[String] =
    AttributeKey("security_rule.ruleset.name")

  /** A rule ID that is unique within the scope of a set or group of agents, observers, or other entities using the rule
    * for detection of this event.
    */
  val SecurityRuleUuid: AttributeKey[String] =
    AttributeKey("security_rule.uuid")

  /** The version / revision of the rule being used for analysis.
    */
  val SecurityRuleVersion: AttributeKey[String] =
    AttributeKey("security_rule.version")

}
