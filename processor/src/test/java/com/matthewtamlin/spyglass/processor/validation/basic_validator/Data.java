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

package com.matthewtamlin.spyglass.processor.validation.basic_validator;

import com.matthewtamlin.spyglass.markers.annotations.conditional_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditional_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.*;
import com.matthewtamlin.spyglass.markers.annotations.use_annotations.*;
import com.matthewtamlin.spyglass.markers.annotations.unconditional_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.unconditional_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.markers.annotations.unconditional_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.markers.annotations.unconditional_handler_annotations.StringHandler;

abstract class Data {
  @Target(isValid = true)
  public void method1() {}
  
  @Target(isValid = true)
  @StringHandler(attributeId = 1)
  protected void method2(Object o) {}
  
  @Target(isValid = false)
  @StringHandler(attributeId = 1)
  @BooleanHandler(attributeId = 1)
  void method3(Object o) {}
  
  @Target(isValid = false)
  @StringHandler(attributeId = 1)
  @BooleanHandler(attributeId = 1)
  @FloatHandler(attributeId = 1)
  void method4(Object o) {}
  
  @Target(isValid = true)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method5() {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  @SpecificEnumHandler(attributeId = 1, handledOrdinal = 1)
  void method6() {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  @StringHandler(attributeId = 1)
  void method7() {}
  
  @Target(isValid = true)
  @BooleanHandler(attributeId = 1)
  @DefaultToBoolean(false)
  void method8(Object o) {}
  
  @Target(isValid = false)
  @StringHandler(attributeId = 1)
  @DefaultToString("something")
  @DefaultToStringResource(resId = 1)
  void method9(Object o) {}
  
  @Target(isValid = false)
  @StringHandler(attributeId = 1)
  @DefaultToString("something")
  @DefaultToStringResource(resId = 1)
  @DefaultToBoolean(false)
  void method10(Object o) {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  @DefaultToBoolean(false)
  void method11() {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  @DefaultToString("something")
  @DefaultToStringResource(resId = 1)
  void method12() {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  @DefaultToString("something")
  @DefaultToStringResource(resId = 1)
  @DefaultToBoolean(false)
  void method13() {}
  
  @Target(isValid = false)
  @DefaultToBoolean(true)
  void method14() {}
  
  @Target(isValid = false)
  @DefaultToString("something")
  @DefaultToNull
  void method15() {}
  
  @Target(isValid = false)
  @DefaultToString("something")
  @DefaultToNull
  @DefaultToEnumConstant(enumClass = Enum.class, ordinal = 1)
  void method16() {}
  
  @Target(isValid = false)
  @DimensionHandler(attributeId = 1)
  void method17() {}
  
  @Target(isValid = true)
  @FloatHandler(attributeId = 1)
  void method18(Object o) {}
  
  @Target(isValid = false)
  @BooleanHandler(attributeId = 1)
  void method19(@UseBoolean(false) Object o) {}
  
  @Target(isValid = false)
  @BooleanHandler(attributeId = 1)
  void method20(Object o1, Object o2) {}
  
  @Target(isValid = true)
  @BooleanHandler(attributeId = 1)
  void method21(@UseByte(1) Object o1, Object o2) {}
  
  @Target(isValid = false)
  @BooleanHandler(attributeId = 1)
  void method22(@UseByte(1) Object o1, @UseLong(1) Object o2) {}
  
  @Target(isValid = false)
  @BooleanHandler(attributeId = 1)
  void method23(Object o1, @UseDouble(1) Object o2, Object o3) {}
  
  @Target(isValid = true)
  @BooleanHandler(attributeId = 1)
  void method24(Object o1, @UseFloat(1F) Object o2, @UseChar('A') Object o3) {}
  
  @Target(isValid = false)
  @BooleanHandler(attributeId = 1)
  void method25(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}
  
  @Target(isValid = true)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method26() {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method27(Object o) {}
  
  @Target(isValid = true)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method28(@UseBoolean(false) Object o) {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method29(Object o1, Object o2) {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method30(@UseByte(1) Object o1, Object o2) {}
  
  @Target(isValid = true)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method31(@UseByte(1) Object o1, @UseLong(1) Object o2) {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method32(Object o1, @UseDouble(2.0) Object o2, Object o3) {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method33(Object o1, @UseFloat(2F) Object o2, @UseChar('A') Object o3) {}
  
  @Target(isValid = true)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method34(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}
  
  @Target(isValid = false)
  @BooleanHandler(attributeId = 1)
  void method35(@UseInt(1) @UseBoolean(true) Object o1, Object o2) {}
  
  @Target(isValid = false)
  @SpecificFlagHandler(attributeId = 1, handledFlags = 1)
  void method36(@UseInt(1) @UseBoolean(true) @UseString("something") Object o1) {}
  
  @Target(isValid = false)
  @BooleanHandler(attributeId = 1)
  private void method37(Object o1) {}
  
  @Target(isValid = true)
  @StringHandler(attributeId = 1)
  abstract void method38(Object o2);
  
  public class NonStaticInnerClass {
    @Target(isValid = false)
    @BooleanHandler(attributeId = 1)
    public void method39(Object o1) {}
  }
  
  public static class StaticInnerClass {
    @Target(isValid = true)
    @BooleanHandler(attributeId = 1)
    public void method41(Object o1) {}
    
    public class NonStaticClassWithinStaticClass {
      @Target(isValid = false)
      @BooleanHandler(attributeId = 1)
      public void method43(Object o1) {}
      
      public void methodContainingAnonymousClass() {
        new Object() {
          @Target(isValid = false)
          @StringHandler(attributeId = 1)
          public void method45(Object o1) {}
        };
      }
      
      public void methodContainingLocalClass() {
        class LocalClass {
          @Target(isValid = false)
          @StringHandler(attributeId = 1)
          public void method46(Object o1) {}
        }
      }
    }
    
    public static class StaticClassWithinStaticClass {
      @Target(isValid = true)
      @BooleanHandler(attributeId = 1)
      public void method47(Object o1) {}
    }
  }
}