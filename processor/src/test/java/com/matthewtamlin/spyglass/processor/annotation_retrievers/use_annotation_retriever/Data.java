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

package com.matthewtamlin.spyglass.processor.annotation_retrievers.use_annotation_retriever;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.*;

public class Data {
  public void method(@ElementId("boolean") @UseBoolean(true) boolean b, Object o) {}

  public void method(Object o, @ElementId("byte") @UseByte(0) byte b) {}

  public void method(@ElementId("char") @UseChar(0) char c) {}

  public void method(@ElementId("double") @UseDouble(0) double d) {}

  public void method(@ElementId("float") @UseFloat(0) float f) {}

  public void method(@ElementId("int") @UseInt(0) int i) {}

  public void method(@ElementId("long") @UseLong(0) long l) {}

  public void method(@ElementId("null") @UseNull Object o) {}

  public void method(@ElementId("short") @UseShort(0) short s) {}

  public void method(@ElementId("string") @UseString("") String s) {}

  public void method(@ElementId("no use-annotation") Object o1, Object o2) {}
}