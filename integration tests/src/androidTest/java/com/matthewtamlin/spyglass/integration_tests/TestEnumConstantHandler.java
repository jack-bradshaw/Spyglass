package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.common.enum_util.EnumInstantiationException;
import com.matthewtamlin.spyglass.integration_tests.enum_constant_handler.EnumConstantHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.enum_constant_handler.EnumForTesting.Fruit;
import com.matthewtamlin.spyglass.integration_tests.enum_constant_handler.WithDefaultToEnumConstantOfInvalidFruit;
import com.matthewtamlin.spyglass.integration_tests.enum_constant_handler.WithDefaultToEnumConstantOfWatermelon;
import com.matthewtamlin.spyglass.integration_tests.enum_constant_handler.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestEnumConstantHandler {
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

	@Test(expected = EnumInstantiationException.class)
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

	@Test(expected = EnumInstantiationException.class)
	@UiThreadTest
	public void testSpyglassFails_attributeMissing_defaultToEnumConstantPresent_defaultToInvalidFruit() {
		final AttributeSet attrs = fromXml(context, R.xml.enum_constant_handler_without_attr);

		final EnumConstantHandlerTestTargetBase target = new WithDefaultToEnumConstantOfInvalidFruit(context, attrs);
	}
}