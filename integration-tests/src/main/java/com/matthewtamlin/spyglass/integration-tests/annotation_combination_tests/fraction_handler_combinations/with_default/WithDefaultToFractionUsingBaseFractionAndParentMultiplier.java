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

package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.fraction_handler_combinations
    .with_default;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.fraction_handler_combinations
    .FractionHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.defaults.DefaultToFractionResource;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.FractionHandler;

public class WithDefaultToFractionUsingBaseFractionAndParentMultiplier extends FractionHandlerTestTargetBase {
  public static final int MULTIPLIER = 17;

  public WithDefaultToFractionUsingBaseFractionAndParentMultiplier(final Context context) {
    super(context);
    init(null, 0, 0);
  }

  public WithDefaultToFractionUsingBaseFractionAndParentMultiplier(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }

  public WithDefaultToFractionUsingBaseFractionAndParentMultiplier(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr) {

    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }

  @TargetApi(21)
  @RequiresApi(21)
  public WithDefaultToFractionUsingBaseFractionAndParentMultiplier(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {

    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }

  @FractionHandler(attributeId = R.styleable.FloatHandlerTestTargetBase_floatHandlerAttr)
  @DefaultToFractionResource(resId = R.fraction.base_fraction_for_testing, parentMultiplier = MULTIPLIER)
  public void handlerMethod(final float f) {
    setReceivedValue(ReceivedValue.of(f));
  }

  private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    WithDefaultToFractionUsingBaseFractionAndParentMultiplier_SpyglassCompanion
        .builder()
        .withTarget(this)
        .withContext(getContext())
        .withStyleableResource(R.styleable.FractionHandlerTestTargetBase)
        .withAttributeSet(attrs)
        .withDefaultStyleAttribute(defStyleAttr)
        .withDefaultStyleResource(defStyleRes)
        .build()
        .passDataToMethods();
  }
}