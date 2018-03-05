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

package com.matthewtamlin.spyglass.integrationtests.annotationcombinations;

import org.junit.runner.RunWith;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorhandler.ColorHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorhandler.WithDefaultToColorResource;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorhandler.WithDefaultToInteger;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorhandler.WithDefaultToIntegerResource;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorhandler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorhandler.WithoutDefault;
import com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestColorHandlerCombinations {
  @Rule
  public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();
  
  private Context context;
  
  @Before
  public void setup() {
    context = InstrumentationRegistry.getTargetContext();
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent_attributeEqualsRed() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_handler_with_attr_equals_red);
    
    final ColorHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(Color.RED)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_handler_without_attr);
    
    final ColorHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<Integer>none()));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToColorResourcePresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_handler_without_attr);
    
    final ColorHandlerTestTargetBase target = new WithDefaultToColorResource(context, attrs);
    
    final int expectedValue = ContextCompat.getColor(context, R.color.ColorForTesting);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToIntegerPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_handler_without_attr);
    
    final ColorHandlerTestTargetBase target = new WithDefaultToInteger(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(WithDefaultToInteger.DEFAULT_VALUE)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToIntegerResourcePresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_handler_without_attr);
    
    final ColorHandlerTestTargetBase target = new WithDefaultToIntegerResource(context, attrs);
    
    final int expectedValue = context.getResources().getInteger(R.integer.IntegerForTesting);
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_noAttributesSupplied_defaultToIntegerPresent() {
    final ColorHandlerTestTargetBase target = new WithDefaultToInteger(context);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(WithDefaultToInteger.DEFAULT_VALUE)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_noAttributesSupplied_noDefaultPresent() {
    final ColorHandlerTestTargetBase target = new WithoutDefault(context);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<Integer>none()));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_handler_without_attr);
    
    final ColorHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<Integer>of(null)));
  }
}