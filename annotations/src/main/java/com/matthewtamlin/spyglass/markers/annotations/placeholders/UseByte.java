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

package com.matthewtamlin.spyglass.markers.annotations.placeholders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the value to supply to the annotated parameter when the Spyglass framework invokes the method. This
 * annotation should only be applied to parameters which satisfy all of the following criteria:
 * <ul>
 * <li>The parameter belongs to a method that has a handler annotation.</li>
 * <li>The parameter has no other use-annotations.</li>
 * <li>The parameter accepts a {@code Number} value.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface UseByte {
  /**
   * @return the value to pass
   */
  byte value();
}