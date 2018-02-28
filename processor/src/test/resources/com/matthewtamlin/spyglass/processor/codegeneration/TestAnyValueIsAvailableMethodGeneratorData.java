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

package com.matthewtamlin.spyglass.processor.codegeneration;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.*;

public class TestAnyValueIsAvailableMethodGeneratorData {
  @ElementId("boolean")
  @BooleanHandler(attributeId = 1)
  public void withBoolean() {}

  @ElementId("color")
  @ColorHandler(attributeId = 1)
  public void withColor() {}

  @ElementId("color state list")
  @ColorStateListHandler(attributeId = 1)
  public void withColorStateList() {}

  @ElementId("dimension")
  @DimensionHandler(attributeId = 1)
  public void withDimension() {}

  @ElementId("drawable")
  @DrawableHandler(attributeId = 1)
  public void withDrawable() {}

  @ElementId("enum constant")
  @EnumConstantHandler(attributeId = 1, enumClass = PlaceholderEnum.class)
  public void withEnumConstant() {}

  @ElementId("enum ordinal")
  @EnumOrdinalHandler(attributeId = 1)
  public void withEnumOrdinal() {}

  @ElementId("float")
  @FloatHandler(attributeId = 1)
  public void withFloat() {}

  @ElementId("fraction")
  @FractionHandler(attributeId = 1)
  public void withFraction() {}

  @ElementId("integer")
  @IntegerHandler(attributeId = 1)
  public void withInteger() {}

  @ElementId("string")
  @StringHandler(attributeId = 1)
  public void withString() {}

  @ElementId("text array")
  public void withTextArray() {}

  @ElementId("text")
  public void withText() {}

  public enum PlaceholderEnum {}
}