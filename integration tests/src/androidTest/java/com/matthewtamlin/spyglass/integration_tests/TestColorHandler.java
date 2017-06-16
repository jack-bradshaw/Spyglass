package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.color_handler.ColorHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithDefaultToColorResource;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithDefaultToInteger;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithDefaultToIntegerResource;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithNoDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestColorHandler {
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

		final ColorHandlerTestTargetBase target = new WithNoDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(Color.RED)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_handler_without_attr);

		final ColorHandlerTestTargetBase target = new WithNoDefault(context, attrs);

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
		final ColorHandlerTestTargetBase target = new WithNoDefault(context);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<Integer>none()));
	}
}