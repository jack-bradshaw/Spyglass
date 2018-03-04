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

package com.matthewtamlin.spyglass.processor.definitions;

import com.google.common.collect.ImmutableSet;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificBooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificFlagHandler;
import com.matthewtamlin.spyglass.markers.annotations.defaults.*;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.*;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.*;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * Source of truth for annotation grouping.
 */
public class AnnotationRegistry {
  public static final Set<Class<? extends Annotation>> UNCONDITIONAL_HANDLERS = ImmutableSet.of(
      BooleanHandler.class,
      ColorHandler.class,
      ColorStateListHandler.class,
      DimensionHandler.class,
      DrawableHandler.class,
      EnumConstantHandler.class,
      EnumOrdinalHandler.class,
      FloatHandler.class,
      FractionHandler.class,
      IntegerHandler.class,
      StringHandler.class);
  
  public static final Set<Class<? extends Annotation>> CONDITIONAL_HANDLERS = ImmutableSet.of(
      SpecificBooleanHandler.class,
      SpecificEnumHandler.class,
      SpecificFlagHandler.class);
  
  public static final Set<Class<? extends Annotation>> DEFAULTS = ImmutableSet.of(
      DefaultToBoolean.class,
      DefaultToBooleanResource.class,
      DefaultToColorResource.class,
      DefaultToColorStateListResource.class,
      DefaultToDimension.class,
      DefaultToDimensionResource.class,
      DefaultToDrawableResource.class,
      DefaultToEnumConstant.class,
      DefaultToFloat.class,
      DefaultToFractionResource.class,
      DefaultToInteger.class,
      DefaultToIntegerResource.class,
      DefaultToNull.class,
      DefaultToString.class,
      DefaultToStringResource.class,
      DefaultToTextArrayResource.class,
      DefaultToTextResource.class);
  
  public static final Set<Class<? extends Annotation>> PLACEHOLDERS = ImmutableSet.of(
      UseBoolean.class,
      UseByte.class,
      UseChar.class,
      UseDouble.class,
      UseFloat.class,
      UseInt.class,
      UseLong.class,
      UseNull.class,
      UseShort.class,
      UseString.class);
}