package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.SpecificFlagHandlerTestTarget;
import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestSpecificFlagHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributePresent_matchesSpecificFlagOnly() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.specific_flag_handler_with_attr_equals_flag_1);

		final SpecificFlagHandlerTestTarget target = new SpecificFlagHandlerTestTarget(context, attrs);

		assertThat(target.wasHandlerCalled(), is(true));
	}

	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributePresent_matchesSpecificFlagAndOthers() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.specific_flag_handler_with_attr_equals_both_flags);

		final SpecificFlagHandlerTestTarget target = new SpecificFlagHandlerTestTarget(context, attrs);

		assertThat(target.wasHandlerCalled(), is(true));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributePresent_doesNotMatchSpecificFlag() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.specific_flag_handler_with_attr_equals_flag_2);

		final SpecificFlagHandlerTestTarget target = new SpecificFlagHandlerTestTarget(context, attrs);

		assertThat(target.wasHandlerCalled(), is(false));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.specific_enum_handler_without_attr);

		final SpecificFlagHandlerTestTarget target = new SpecificFlagHandlerTestTarget(context, attrs);

		assertThat(target.wasHandlerCalled(), is(false));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_noAttributesSupplied() {
		final SpecificFlagHandlerTestTarget target = new SpecificFlagHandlerTestTarget(context);

		assertThat(target.wasHandlerCalled(), is(false));
	}
}