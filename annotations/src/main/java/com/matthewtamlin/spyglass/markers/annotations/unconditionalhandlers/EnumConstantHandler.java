/*
 * Copyright 2017-2018 Matthew David Tamlin
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

package com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling an enum attribute in the form of an enum constant object. If the Spyglass
 * framework finds an integer value mapped to the attribute ID, it will use the supplied {@code enumClass()} to get a
 * reference to the enum constant with the mapped value as its ordinal. The framework will then invoke the method and
 * pass in the constant. This annotation should only be applied to methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method is a non-static member of an Android View subclass.</li>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one parameter of the specified enum type.</li>
 * <li>Every parameter belonging to the method has a placeholder annotation, except for one parameter of the specified enum
 * type.</li>
 * </ul>
 * <p>
 * Important note: An exception will be thrown at runtime if the value passd to the attribute does not correspond to an
 * ordinal of the enum class.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface EnumConstantHandler {
  /**
   * @return the resource ID of the handled attribute
   */
  int attributeId();
  
  /**
   * @return the enum type to use
   */
  Class<? extends Enum> enumClass();
}