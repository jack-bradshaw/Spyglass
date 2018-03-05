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

package com.matthewtamlin.spyglass.integrationtests.miscellaneous;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.StringHandler;

public class OverloadBehaviourTestTarget extends View {
  private ReceivedValue<String> positiveReceivedValue = ReceivedValue.none();
  
  private ReceivedValue<String> negativeReceivedValue = ReceivedValue.none();
  
  public OverloadBehaviourTestTarget(final Context context) {
    super(context);
    init(null, 0, 0);
  }
  
  public OverloadBehaviourTestTarget(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }
  
  public OverloadBehaviourTestTarget(final Context context, final AttributeSet attrs, final int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }
  
  @TargetApi(21)
  @RequiresApi(21)
  public OverloadBehaviourTestTarget(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {
    
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }
  
  @StringHandler(attributeId = R.styleable.OverloadBehaviourTestTarget_overloadAttr)
  public void handlerMethodWithAnnotation(final String arg0) {
    positiveReceivedValue = ReceivedValue.of(arg0);
  }
  
  public void handlerMethodWithoutAnnotation(final String arg0) {
    negativeReceivedValue = ReceivedValue.of(arg0);
  }
  
  public ReceivedValue<String> getPositiveReceivedValue() {
    return positiveReceivedValue;
  }
  
  public ReceivedValue<String> getNegativeReceivedValue() {
    return negativeReceivedValue;
  }
  
  private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    OverloadBehaviourTestTarget_SpyglassCompanion
        .builder()
        .setTarget(this)
        .setContext(getContext())
        .setStyleableResource(R.styleable.OverloadBehaviourTestTarget)
        .setAttributeSet(attrs)
        .setDefaultStyleAttribute(defStyleAttr)
        .setDefaultStyleResource(defStyleRes)
        .build()
        .callTargetMethodsNow();
  }
}