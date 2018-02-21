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

package com.matthewtamlin.spyglass.processor.annotation_retrievers.value_handler_annotation_retriever;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificFlagHandler;
import com.matthewtamlin.spyglass.markers.annotations.defaults.*;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.*;

import static com.matthewtamlin.spyglass.markers.units.DimensionUnit.DP;

public class Data {
  @ElementId("boolean")
  @BooleanHandler(attributeId = 0)
  public void method1() {}

  @ElementId("color")
  @ColorHandler(attributeId = 0)
  public void method2() {}

  @ElementId("color state list")
  @ColorStateListHandler(attributeId = 0)
  public void method3() {}

  @ElementId("dimension")
  @DimensionHandler(attributeId = 0)
  public void method4() {}

  @ElementId("drawable")
  @DrawableHandler(attributeId = 0)
  public void method5() {}

  @ElementId("enum constant")
  @EnumConstantHandler(attributeId = 0, enumClass = PlaceholderEnum.class)
  public void method6() {}

  @ElementId("enum ordinal")
  @EnumOrdinalHandler(attributeId = 0)
  public void method7() {}

  @ElementId("float")
  @FloatHandler(attributeId = 0)
  public void method8() {}

  @ElementId("fraction")
  @FractionHandler(attributeId = 0)
  public void method9() {}

  @ElementId("integer")
  @IntegerHandler(attributeId = 0)
  public void method10() {}

  @ElementId("string")
  @StringHandler(attributeId = 0)
  public void method11() {}

  @ElementId("text array")
  public void method12() {}

  @ElementId("text")
  public void method13() {}

  @ElementId("no value handler annotation")
  @DefaultToBoolean(true)
  @DefaultToBooleanResource(resId = 0)
  @DefaultToColorResource(resId = 0)
  @DefaultToColorStateListResource(resId = 0)
  @DefaultToDimension(value = 0, unit = DP)
  @DefaultToDimensionResource(resId = 0)
  @DefaultToDrawableResource(resId = 0)
  @DefaultToEnumConstant(enumClass = PlaceholderEnum.class, ordinal = 0)
  @DefaultToFloat(0)
  @DefaultToFractionResource(resId = 0, baseMultiplier = 0, parentMultiplier = 0)
  @DefaultToInteger(0)
  @DefaultToIntegerResource(resId = 0)
  @DefaultToNull
  @DefaultToString("hello world")
  @DefaultToStringResource(resId = 0)
  @DefaultToTextArrayResource(resId = 0)
  @DefaultToTextResource(resId = 0)
  @SpecificEnumHandler(attributeId = 0, handledOrdinal = 0)
  @SpecificFlagHandler(attributeId = 0, handledFlags = 0)
  public void method14() {}

  private enum PlaceholderEnum {}
}