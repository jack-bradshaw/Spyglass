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
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.booleanhandler.BooleanHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.booleanhandler.WithDefaultToBoolean;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.booleanhandler.WithDefaultToBooleanResource;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.booleanhandler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.booleanhandler.WithoutDefault;
import com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestBooleanHandlerCombinations {
  @Rule
  public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();
  
  private Context context;
  
  @Before
  public void setup() {
    context = InstrumentationRegistry.getTargetContext();
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributePresent_attributeEqualsTrue() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.boolean_handler_with_attr_equals_true);
    
    final BooleanHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(true)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributePresent_attributeEqualsFalse() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.boolean_handler_with_attr_equals_false);
    
    final BooleanHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(false)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.boolean_handler_without_attr);
    
    final BooleanHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<Boolean>none()));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributeMissing_defaultToBooleanPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.boolean_handler_without_attr);
    
    final BooleanHandlerTestTargetBase target = new WithDefaultToBoolean(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(WithDefaultToBoolean.DEFAULT_VALUE)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributeMissing_defaultToBooleanResourcePresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.boolean_handler_without_attr);
    
    final BooleanHandlerTestTargetBase target = new WithDefaultToBooleanResource(context, attrs);
    
    final boolean defaultValue = context.getResources().getBoolean(R.bool.BooleanForTesting);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.of(defaultValue)));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.boolean_handler_without_attr);
    
    final BooleanHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<Boolean>of(null)));
  }
}