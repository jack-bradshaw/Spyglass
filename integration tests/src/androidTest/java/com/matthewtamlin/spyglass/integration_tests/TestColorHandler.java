package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.color_handler.ColorHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithDefaultToColorResource;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithDefaultToInteger;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithDefaultToIntegerResource;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integration_tests.color_handler.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
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