/*
 * Copyright 2017 Matthew David Tamlin
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

package com.matthewtamlin.spyglass.markers.annotations.unconditional_handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a fraction attribute. If the Spyglass framework finds a fraction value mapped
 * to the attribute ID, it will invoke the method and pass in the value after applying the fraction multiplication rule.
 * <p>
 * Android defines two types of fraction resources: base fractions and parent fractions. If the fraction is defined
 * in resources as a base type, then it will be multiplied by the {@code baseMultiplier} before being passed in. If
 * the fraction is defined in resources as a parent type, then it will be multiplied by the {@code parentMultiplier}
 * before being passed in. By default, both multipliers are set to 1.
 * <p>
 * This annotation should only be applied to methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method is a non-static member of an Android View subclass.</li>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one {@code Number} parameter.</li>
 * <li>Except for one {@code Number} parameter, every parameter belonging to the method has a use-annotation.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface FractionHandler {
  /**
   * @return the resource ID of the handled attribute
   */
  int attributeId();

  /**
   * @return the value to multiply base fractions by, defaults to 1
   */
  int baseMultiplier() default 1;

  /**
   * @return the value to multiply parent fractions by, defaults to 1
   */
  int parentMultiplier() default 1;
}