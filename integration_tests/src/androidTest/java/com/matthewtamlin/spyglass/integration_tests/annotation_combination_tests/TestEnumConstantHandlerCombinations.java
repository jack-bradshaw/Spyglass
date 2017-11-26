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
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.core.TargetException;
import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.EnumConstantHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.EnumForTesting.Fruit;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.WithDefaultToEnumConstantOfWatermelon;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.WithDefaultToEnumConstantWithOrdinalTooBig;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.WithDefaultToEnumConstantWithOrdinalTooSmall;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.WithDefaultToNull;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.enum_constant_handler_combinations.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestEnumConstantHandlerCombinations {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent_attributeEqualsPear() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_constant_handler_with_attr_equals_pear);

		final EnumConstantHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(Fruit.PEAR)));
	}

	@Test(expected = TargetException.class)
	@UiThreadTest
	public void testSpyglassFails_attributePresent_attributeEqualsInvalidFruit() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_constant_handler_with_attr_equals_invalid_fruit);

		final EnumConstantHandlerTestTargetBase target = new WithoutDefault(context, attrs);
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_constant_handler_without_attr);

		final EnumConstantHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Fruit>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToEnumConstantPresent_defaultToWatermelon() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_constant_handler_without_attr);

		final EnumConstantHandlerTestTargetBase target = new WithDefaultToEnumConstantOfWatermelon(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(Fruit.WATERMELON)));
	}

	@Test(expected = TargetException.class)
	@UiThreadTest
	public void testSpyglassFails_attributeMissing_defaultToEnumConstantPresent_defaultOrdinalTooSmall() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_constant_handler_without_attr);

		new WithDefaultToEnumConstantWithOrdinalTooSmall(context, attrs);
	}

	@Test(expected = TargetException.class)
	@UiThreadTest
	public void testSpyglassFails_attributeMissing_defaultToEnumConstantPresent_defaultOrdinalTooBig() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_constant_handler_without_attr);

		new WithDefaultToEnumConstantWithOrdinalTooBig(context, attrs);
	}


	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.enum_constant_handler_without_attr);

		final EnumConstantHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Fruit>of(null)));
	}
}