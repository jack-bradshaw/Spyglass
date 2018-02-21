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

package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.use_annotation_combinations.*;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestUseAnnotationCombinations {
  @Rule
  public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();
  
  private Context context;
  
  @Before
  public void setup() {
    context = InstrumentationRegistry.getTargetContext();
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseNumberOnMatchingPrimitiveTypes() {
    final WithUseNumberOnMatchingPrimitiveTypes target = new WithUseNumberOnMatchingPrimitiveTypes(context);
    
    checkEquality(target);
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseNumberOnMatchingBoxedTypes() {
    final WithUseNumberOnMatchingBoxedTypes target = new WithUseNumberOnMatchingBoxedTypes(context);
    
    checkEquality(target);
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseNumberOnBaseNumberType() {
    final WithUseNumberOnBaseNumberType target = new WithUseNumberOnBaseNumberType(context);
    
    checkEquality(target);
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseNumberRequiringConversionToOtherBoxedNumberTypes() {
    final WithUseNumberRequiringConversionToOtherBoxedNumberTypes
        target = new WithUseNumberRequiringConversionToOtherBoxedNumberTypes(context);
    
    checkEquality(target);
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseNumberRequiringConversionToOtherPrimitiveNumberTypes() {
    final WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes
        target = new WithUseNumberRequiringConversionToOtherPrimitiveNumberTypes(context);
    
    checkEquality(target);
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseNull() {
    final WithUseNull target = new WithUseNull(context);
    
    checkEquality(target);
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseString() {
    final WithUseString target = new WithUseString(context);
    
    checkEquality(target);
  }
  
  @Test
  @UiThreadTest
  public void testSpyglassPassesCorrectData_withUseChar() {
    final WithUseChar target = new WithUseChar(context);
    
    checkEquality(target);
  }
  
  private void checkEquality(final UseAnnotationsTestTargetBase target) {
    final ReceivedValue<List<Object>> receivedValues = target.getReceivedValues();
    final ReceivedValue<List<Object>> expectedReceivedValues = target.getExpectedReceivedValues();
    
    if (!receivedValues.exists() || !expectedReceivedValues.exists()) {
      assertThat(receivedValues.exists(), is(expectedReceivedValues.exists()));
    }
    
    final List<Object> receivedList = receivedValues.get();
    final List<Object> expectedReceivedList = expectedReceivedValues.get();
    
    for (int i = 0; i < receivedList.size(); i++) {
      if (receivedList.get(i) instanceof Number && expectedReceivedList.get(i) instanceof Number) {
        final Number received = (Number) receivedList.get(i);
        final Number expected = (Number) expectedReceivedList.get(i);
        
        assertThat(received.doubleValue(), is(expected.doubleValue()));
      } else {
        assertThat(receivedList.get(i), is(expectedReceivedList.get(i)));
      }
    }
  }
}