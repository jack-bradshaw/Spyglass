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

package com.matthewtamlin.spyglass.processor.annotationretrievers.defaultretriever;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificFlagHandler;
import com.matthewtamlin.spyglass.markers.annotations.defaults.*;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.*;

import static com.matthewtamlin.spyglass.markers.units.DimensionUnit.DP;

public class Data {
  @ElementId("boolean")
  @DefaultToBoolean(true)
  public void method1() {}

  @ElementId("boolean resource")
  @DefaultToBooleanResource(resId = 0)
  public void method2() {}

  @ElementId("color resource")
  @DefaultToColorResource(resId = 0)
  public void method3() {}

  @ElementId("color state list resource")
  @DefaultToColorStateListResource(resId = 0)
  public void method4() {}

  @ElementId("dimension")
  @DefaultToDimension(value = 0, unit = DP)
  public void method5() {}

  @ElementId("dimension resource")
  @DefaultToDimensionResource(resId = 0)
  public void method6() {}

  @ElementId("drawable resource")
  @DefaultToDrawableResource(resId = 0)
  public void method7() {}

  @ElementId("enum constant")
  @DefaultToEnumConstant(enumClass = PlaceholderEnum.class, ordinal = 0)
  public void method8() {}

  @ElementId("float")
  @DefaultToFloat(0)
  public void method9() {}

  @ElementId("fraction resource")
  @DefaultToFractionResource(resId = 0, baseMultiplier = 0, parentMultiplier = 0)
  public void method10() {}

  @ElementId("integer")
  @DefaultToInteger(0)
  public void method11() {}

  @ElementId("integer resource")
  @DefaultToIntegerResource(resId = 0)
  public void method12() {}

  @ElementId("null")
  @DefaultToNull()
  public void method13() {}

  @ElementId("string")
  @DefaultToString("hello world")
  public void method14() {}

  @ElementId("string resource")
  @DefaultToStringResource(resId = 0)
  public void method15() {}

  @ElementId("text array resource")
  @DefaultToTextArrayResource(resId = 0)
  public void method16() {}

  @ElementId("text resource")
  @DefaultToTextResource(resId = 0)
  public void method17() {}

  @ElementId("no default annotation")
  @BooleanHandler(attributeId = 0)
  @ColorHandler(attributeId = 0)
  @ColorStateListHandler(attributeId = 0)
  @DimensionHandler(attributeId = 0)
  @DrawableHandler(attributeId = 0)
  @EnumConstantHandler(attributeId = 0, enumClass = PlaceholderEnum.class)
  @EnumOrdinalHandler(attributeId = 0)
  @FloatHandler(attributeId = 0)
  @FractionHandler(attributeId = 0)
  @IntegerHandler(attributeId = 0)
  @StringHandler(attributeId = 0)
  @SpecificEnumHandler(attributeId = 0, handledOrdinal = 0)
  @SpecificFlagHandler(attributeId = 0, handledFlags = 0)
  public void method18() {}

  private enum PlaceholderEnum {}
}