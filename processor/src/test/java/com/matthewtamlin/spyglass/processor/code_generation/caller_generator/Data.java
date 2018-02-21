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

package com.matthewtamlin.spyglass.processor.code_generation.caller_generator;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.defaults.DefaultToBoolean;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.BooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.StringHandler;

public class Data {
  @ElementId("no handler")
  public void noHandler() {}

  @ElementId("value handler with default")
  @BooleanHandler(attributeId = 1)
  @DefaultToBoolean(false)
  public void valueHandlerAndDefault(boolean b) {}

  @ElementId("value handler no default")
  @StringHandler(attributeId = 1)
  public void valueHandlerOnly(String s) {}

  @ElementId("call handler")
  @SpecificEnumHandler(attributeId = 1, handledOrdinal = 1)
  public void callHandler() {}
}