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

package com.matthewtamlin.spyglass.integrationtests.annotationcombinations.placeholderannotations;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.markers.annotations.defaults.DefaultToString;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseByte;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseDouble;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseFloat;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseInt;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseLong;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseShort;
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.StringHandler;

import java.util.ArrayList;
import java.util.List;

public class WithUseNumberRequiringConversionToOtherBoxedNumberTypes extends PlaceholderTestTargetBase {
  public WithUseNumberRequiringConversionToOtherBoxedNumberTypes(final Context context) {
    super(context);
    init(null, 0, 0);
  }

  public WithUseNumberRequiringConversionToOtherBoxedNumberTypes(final Context context, final AttributeSet attrs) {
    super(context, attrs);
    init(attrs, 0, 0);
  }

  public WithUseNumberRequiringConversionToOtherBoxedNumberTypes(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr) {

    super(context, attrs, defStyleAttr);
    init(attrs, defStyleAttr, 0);
  }

  @RequiresApi(21)
  @TargetApi(21)
  public WithUseNumberRequiringConversionToOtherBoxedNumberTypes(
      final Context context,
      final AttributeSet attrs,
      final int defStyleAttr,
      final int defStyleRes) {

    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs, defStyleAttr, defStyleRes);
  }

  @StringHandler(attributeId = R.styleable.PlaceholderTestTargetBase_placeholderAttr)
  @DefaultToString("default value")
  public void handlerMethod(
      final String arg0,
      @UseByte(30) final Double arg1,
      @UseDouble(10.5) final Float arg2,
      @UseFloat(40.8F) final Integer arg3,
      @UseInt(9) final Long arg4,
      @UseLong(123456789123456789L) final Short arg5,
      @UseShort(2) final Byte arg6) {

    final List<Object> invocationArgs = new ArrayList<>();

    invocationArgs.add(arg0);
    invocationArgs.add(arg1);
    invocationArgs.add(arg2);
    invocationArgs.add(arg3);
    invocationArgs.add(arg4);
    invocationArgs.add(arg5);
    invocationArgs.add(arg6);

    setReceivedValue(ReceivedValue.of(invocationArgs));
  }

  @Override
  public ReceivedValue<List<Object>> getExpectedReceivedValues() {
    final List<Object> expectedArgs = new ArrayList<>();

    expectedArgs.add("default value");
    expectedArgs.add(30);
    expectedArgs.add(10.5);
    expectedArgs.add(((Float) 40.8F).intValue());
    expectedArgs.add(9);
    expectedArgs.add((short) 123456789123456789L);
    expectedArgs.add(2);

    return ReceivedValue.of(expectedArgs);
  }

  private void init(final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
    WithUseNumberRequiringConversionToOtherBoxedNumberTypes_SpyglassCompanion
        .builder()
        .setTarget(this)
        .setContext(getContext())
        .setStyleableResource(R.styleable.PlaceholderTestTargetBase)
        .setAttributeSet(attrs)
        .setDefaultStyleAttribute(defStyleAttr)
        .setDefaultStyleResource(defStyleRes)
        .build()
        .callTargetMethodsNow();
  }
}