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

package com.matthewtamlin.spyglass.integrationtests.annotationcombinations.specificbooleanhandler;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificBooleanHandler;

public class SpecificBooleanHandlerTestTarget extends View {
  private boolean handlerCalled = false;

  public SpecificBooleanHandlerTestTarget(final Context context) {
    super(context);
    init(null, 0, 0);
  }

  public SpecificBooleanHandlerTestTarget(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }

  public SpecificBooleanHandlerTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }

  @RequiresApi(21)
  @TargetApi(21)
  public SpecificBooleanHandlerTestTarget(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {

    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }

  @SpecificBooleanHandler(
      attributeId = R.styleable.SpecificFlagHandlerTestTarget_specificFlagHandlerAttr,
      handledBoolean = true)
  public void handlerMethod() {
    handlerCalled = true;
  }

  public boolean wasHandlerCalled() {
    return handlerCalled;
  }

  private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    SpecificBooleanHandlerTestTarget_SpyglassCompanion
        .builder()
        .withTarget(this)
        .withContext(getContext())
        .withStyleableResource(R.styleable.SpecificBooleanHandlerTestTarget)
        .withAttributeSet(attrs)
        .withDefaultStyleAttribute(defStyleAttr)
        .withDefaultStyleResource(defStyleRes)
        .build()
        .passDataToMethods();
  }
}