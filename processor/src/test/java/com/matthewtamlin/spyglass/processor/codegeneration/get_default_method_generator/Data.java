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

package com.matthewtamlin.spyglass.processor.codegeneration.get_default_method_generator;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.defaults.*;

import static com.matthewtamlin.spyglass.markers.units.DimensionUnit.DP;

public class Data {
  @ElementId("boolean")
  @DefaultToBoolean(true)
  public void withBoolean() {}

  @ElementId("boolean resource")
  @DefaultToBooleanResource(resId = 1)
  public void withBooleanRes() {}

  @ElementId("color resource")
  @DefaultToColorResource(resId = 1)
  public void withColorRes() {}

  @ElementId("color state list resource")
  @DefaultToColorStateListResource(resId = 1)
  public void withColorStateListRes() {}

  @ElementId("dimension")
  @DefaultToDimension(value = 1, unit = DP)
  public void withDimension() {}

  @ElementId("dimension resource")
  @DefaultToDimensionResource(resId = 1)
  public void withDimensionRes() {}

  @ElementId("drawable resource")
  @DefaultToDrawableResource(resId = 1)
  public void withDrawableRes() {}

  @ElementId("enum constant")
  @DefaultToEnumConstant(enumClass = PlaceholderEnum.class, ordinal = 0)
  public void withEnumConstant() {}

  @ElementId("float")
  @DefaultToFloat(1F)
  public void withFloat() {}

  @ElementId("fraction resource")
  @DefaultToFractionResource(resId = 1)
  public void withFractionRes() {}

  @ElementId("integer")
  @DefaultToInteger(1)
  public void withInteger() {}

  @ElementId("integer resource")
  @DefaultToIntegerResource(resId = 1)
  public void withIntegerRes() {}

  @ElementId("null")
  @DefaultToNull
  public void withNull() {}

  @ElementId("string")
  @DefaultToString("")
  public void withString() {}

  @ElementId("string resource")
  @DefaultToStringResource(resId = 1)
  public void withStringRes() {}

  @ElementId("text array")
  @DefaultToTextArrayResource(resId = 1)
  public void withTextArrayRes() {}

  @ElementId("text")
  @DefaultToTextResource(resId = 1)
  public void withTextRes() {}

  public enum PlaceholderEnum {}
}