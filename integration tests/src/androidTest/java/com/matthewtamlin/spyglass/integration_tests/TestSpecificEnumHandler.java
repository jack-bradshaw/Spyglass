package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestSpecificEnumHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributePresentAndCorrectOrdinal() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.specific_enum_handler_with_attr_correct_ordinal);

		final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context, attrs);

		assertThat(target.wasHandlerCalled(), is(true));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributePresentButIncorrectOrdinal() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.specific_enum_handler_with_attr_incorrect_ordinal);

		final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context, attrs);

		assertThat(target.wasHandlerCalled(), is(false));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.specific_enum_handler_without_attr);

		final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context, attrs);

		assertThat(target.wasHandlerCalled(), is(false));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_noAttributesSupplied() {
		final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context);

		assertThat(target.wasHandlerCalled(), is(false));
	}
}