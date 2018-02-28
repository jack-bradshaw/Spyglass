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
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import com.matthewtamlin.spyglass.integrationtests.R;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.drawablehandler.DrawableHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.drawablehandler.WithDefaultToDrawable;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.drawablehandler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integrationtests.annotationcombinations.drawablehandler.WithoutDefault;
import com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integrationtests.framework.ReceivedValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.integrationtests.framework.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestDrawableHandlerCombinations {
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
    final AttributeSet attrs = fromXml(context, R.xml.drawable_handler_with_attr_equals_main_drawable);
    
    final DrawableHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    final Drawable expectedValue = ContextCompat.getDrawable(context, R.drawable.main_drawable_for_testing);
    checkReceivedDrawablesAreEqual(target.getReceivedValue(), ReceivedValue.of(expectedValue));
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
    final AttributeSet attrs = fromXml(context, R.xml.drawable_handler_without_attr);
    
    final DrawableHandlerTestTargetBase target = new WithoutDefault(context, attrs);
    
    checkReceivedDrawablesAreEqual(target.getReceivedValue(), ReceivedValue.<Drawable>none());
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_attributeMissing_defaultToDrawablePresent() {
    final AttributeSet attrs = fromXml(context, R.xml.drawable_handler_without_attr);
    
    final DrawableHandlerTestTargetBase target = new WithDefaultToDrawable(context, attrs);
    
    final Drawable expectedValue = ContextCompat.getDrawable(context, R.drawable.default_drawable_for_testing);
    checkReceivedDrawablesAreEqual(target.getReceivedValue(), ReceivedValue.of(expectedValue));
  }
  
  private boolean checkReceivedDrawablesAreEqual(
      final ReceivedValue<Drawable> arg1,
      final ReceivedValue<Drawable> arg2) {
    
    checkNotNull(arg1, "Argument \'arg1\' cannot be null.");
    checkNotNull(arg1, "Argument \'arg2\' cannot be null.");
    
    if (!arg1.exists() || !arg2.exists()) {
      return !arg1.exists() && !arg2.exists();
      
    } else if (arg1.get() == null || arg2.get() == null) {
      return arg1.get() == arg2.get();
      
    } else {
      return arg1.get().getConstantState().equals(arg2.get().getConstantState());
    }
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
    final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.drawable_handler_without_attr);
    
    final DrawableHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);
    
    assertThat(target.getReceivedValue(), is(ReceivedValue.<Drawable>of(null)));
  }
}