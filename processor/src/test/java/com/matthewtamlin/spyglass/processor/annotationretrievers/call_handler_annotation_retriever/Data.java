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

package com.matthewtamlin.spyglass.processor.annotationretrievers.call_handler_annotation_retriever;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificBooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificFlagHandler;
import com.matthewtamlin.spyglass.markers.annotations.defaults.*;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.*;

import static com.matthewtamlin.spyglass.markers.units.DimensionUnit.DP;

public class Data {
  @ElementId("specific boolean")
  @SpecificBooleanHandler(attributeId = 0, handledBoolean = true)
  public void method1() {}

  @ElementId("specific enum")
  @SpecificEnumHandler(attributeId = 0, handledOrdinal = 1)
  public void method2() {}

  @ElementId("specific flag")
  @SpecificFlagHandler(attributeId = 0, handledFlags = 1)
  public void method3() {}

  @ElementId("no call handler annotation")
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
  public void method4() {}

  private enum PlaceholderEnum {}
}