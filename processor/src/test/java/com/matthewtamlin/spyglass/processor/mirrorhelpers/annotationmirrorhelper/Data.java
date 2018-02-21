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

package com.matthewtamlin.spyglass.processor.mirrorhelpers.annotationmirrorhelper;

import com.matthewtamlin.avatar.rules.ElementId;

public class Data {
  public static final String SPECIFIED_VALUE = "specified value";

  @ElementId("get annotation mirror: without annotation")
  public String field1;

  @ElementId("get annotation mirror: with annotation")
  @SomeAnnotationWithValue
  public String field2;

  @ElementId("get annotation value ignoring defaults: no value")
  @SomeAnnotationWithValue()
  public Object field3;

  @ElementId("get annotation value ignoring defaults: with value")
  @SomeAnnotationWithValue(value = SPECIFIED_VALUE)
  public Object field4;

  @ElementId("get annotation value with defaults: no value")
  @SomeAnnotationWithValue()
  public Object field5;

  @ElementId("get annotation value with defaults: with value")
  @SomeAnnotationWithValue(value = SPECIFIED_VALUE)
  public Object field6;
}