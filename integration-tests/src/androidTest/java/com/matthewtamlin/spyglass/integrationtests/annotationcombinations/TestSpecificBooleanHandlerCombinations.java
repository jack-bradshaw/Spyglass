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

package com.matthewtamlin.spyglass.integrationtests.annotationcombinations;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.specificbooleanhandler.SpecificBooleanHandlerTestTarget;
import com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSpecificBooleanHandlerCombinations {
  @Rule
  public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();
  
  private Context context;
  
  @Before
  public void setup() {
    context = InstrumentationRegistry.getTargetContext();
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributePresent_matchesSpecificBoolean() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(
        context,
        R.xml.specific_boolean_handler_with_attr_equals_true);
    
    final SpecificBooleanHandlerTestTarget target = new SpecificBooleanHandlerTestTarget(context, attrs);
    
    assertThat(target.wasHandlerCalled(), is(true));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_attributePresent_doesNotMatchSpecificBoolean() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(
        context,
        R.xml.specific_boolean_handler_with_attr_equals_false);
    
    final SpecificBooleanHandlerTestTarget target = new SpecificBooleanHandlerTestTarget(context, attrs);
    
    assertThat(target.wasHandlerCalled(), is(false));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_attributeMissing() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(
        context,
        R.xml.specific_boolean_handler_without_attr);
    
    final SpecificBooleanHandlerTestTarget target = new SpecificBooleanHandlerTestTarget(context, attrs);
    
    assertThat(target.wasHandlerCalled(), is(false));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_noAttributesSupplied() {
    final SpecificBooleanHandlerTestTarget target = new SpecificBooleanHandlerTestTarget(context);
    
    assertThat(target.wasHandlerCalled(), is(false));
  }
}