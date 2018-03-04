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
import com.matthewtamlin.spyglass.markers.annotations.placeholders.*;

public class TestGetPlaceholderMethodGeneratorData {
  public void method(@UseBoolean(true) @ElementId("boolean") boolean b) {}
  
  public void method(@UseByte(1) @ElementId("byte") byte b) {}
  
  public void method(@UseChar('a') @ElementId("char") char c) {}
  
  public void method(@UseDouble(10.2) @ElementId("double") double d) {}
  
  public void method(@UseFloat(20.8F) @ElementId("float") float f) {}
  
  public void method(@UseInt(9) @ElementId("int") int i) {}
  
  public void method(@UseLong(9L) @ElementId("long") long l) {}
  
  public void method(@UseNull @ElementId("null") Object o) {}
  
  public void method(@UseShort(2) @ElementId("short") short s) {}
  
  public void method(@UseString("") @ElementId("string") String s) {}
  
  public void method(@ElementId("none") Void v) {}
}