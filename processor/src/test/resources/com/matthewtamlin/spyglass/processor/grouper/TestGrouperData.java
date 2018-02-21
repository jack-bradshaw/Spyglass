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

package com.matthewtamlin.spyglass.processor.grouper;


import com.matthewtamlin.avatar.rules.ElementId;

@ElementId("primary class")
public class TestGrouperData {
  @ElementId("primary class method")
  public void outerMethod() {}

  @ElementId("primary class field")
  public String outerField;

  @ElementId("inner class")
  public static class InnerClass {
    @ElementId("inner class method")
    public void innerMethod() {}

    @ElementId("inner class field")
    public boolean innerField;
  }

  public static class SomeClass {
    public static class SomeOtherClass {
      @ElementId("very nested class")
      public static class NestedClass {
        @ElementId("very nested class method")
        public void innerInnerInnerMethod() {}

        @ElementId("very nested class field")
        public int innerField;
      }
    }
  }
}

@ElementId("secondary class")
class SecondaryClass {
  @ElementId("secondary class method")
  public void secondaryMethod() {}

  @ElementId("secondary class field")
  public int secondaryField;
}