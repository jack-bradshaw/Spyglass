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

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorstatelisthandler.ColorStateListHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorstatelisthandler.WithDefaultToColorStateListResource;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorstatelisthandler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.colorstatelisthandler.WithoutDefault;
import com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestColorStateListHandlerCombinations {
  @Rule
  public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();
  
  private Context context;
  
  @Before
  public void setup() {
    context = InstrumentationRegistry.getTargetContext();
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributePresent() {
    final AttributeSet attrs = fromXml(context, R.xml.color_state_list_handler_with_attr_equals_main_csl);
    
    final ColorStateListHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    final ColorStateList expectedValue = ContextCompat.getColorStateList(
        context,
        R.color.main_color_state_list_for_testing);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
    final AttributeSet attrs = fromXml(context, R.xml.color_state_list_handler_without_attr);
    
    final ColorStateListHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>none()));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToColorStateListPresent() {
    final AttributeSet attrs = fromXml(context, R.xml.color_state_list_handler_without_attr);
    
    final ColorStateListHandlerTestTargetBase target = new WithDefaultToColorStateListResource(context, attrs);
    
    final ColorStateList expectedValue = ContextCompat.getColorStateList(
        context,
        R.color.default_color_state_list_for_testing);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesDataCorrectly_noAttributesSupplied_defaultToColorStateListPresent() {
    final ColorStateListHandlerTestTargetBase target = new WithDefaultToColorStateListResource(context);
    
    final ColorStateList expectedValue = ContextCompat.getColorStateList(
        context,
        R.color.default_color_state_list_for_testing);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesDataCorrectly_noAttributesSupplied_noDefaultPresent() {
    final ColorStateListHandlerTestTargetBase target = new WithoutDefault(context);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>none()));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_state_list_handler_without_attr);
    
    final ColorStateListHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>of(null)));
  }
}