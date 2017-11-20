package com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.annotation_combination_tests.specific_boolean_handler_combinations.SpecificBooleanHandlerTestTarget;
import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;

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