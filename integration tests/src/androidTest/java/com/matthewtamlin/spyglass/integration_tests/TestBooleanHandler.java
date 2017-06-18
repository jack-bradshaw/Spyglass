package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.boolean_handler.BooleanHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.boolean_handler.WithDefaultToBoolean;
import com.matthewtamlin.spyglass.integration_tests.boolean_handler.WithDefaultToBooleanResource;
import com.matthewtamlin.spyglass.integration_tests.boolean_handler.WithoutDefault;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;
import com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestBooleanHandler {
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
}