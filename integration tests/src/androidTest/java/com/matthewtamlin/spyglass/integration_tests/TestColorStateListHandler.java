package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.color_state_list_handler_test_target.ColorStateListHandlerTestTarget;
import com.matthewtamlin.spyglass.integration_tests.color_state_list_handler_test_target.WithDefaultToColorStateListResource;
import com.matthewtamlin.spyglass.integration_tests.color_state_list_handler_test_target.WithNoDefault;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier.fromXml;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestColorStateListHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	private ColorStateList mainColorStateList;

	private ColorStateList defaultColorStateList;

	private AttributeSet attributePresent;

	private AttributeSet attributeMissing;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();

		mainColorStateList = ContextCompat.getColorStateList(context, R.color.main_color_state_list_for_testing);
		defaultColorStateList = ContextCompat.getColorStateList(context, R.color.default_color_state_list_for_testing);

		attributePresent = fromXml(context, R.xml.color_state_list_handler_with_attr_equals_main_csl);
		attributeMissing = fromXml(context, R.xml.color_state_list_handler_without_attr);
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent() {
		final ColorStateListHandlerTestTarget target = new WithNoDefault(context, attributePresent);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(mainColorStateList)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final ColorStateListHandlerTestTarget target = new WithNoDefault(context, attributeMissing);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToColorStateListPresent() {
		final ColorStateListHandlerTestTarget target = new WithDefaultToColorStateListResource(
				context,
				attributeMissing);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(defaultColorStateList)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesDataCorrectly_noAttributesSupplied_defaultToColorStateListPresent() {
		final ColorStateListHandlerTestTarget target = new WithDefaultToColorStateListResource(context);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(defaultColorStateList)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesDataCorrectly_noAttributesSupplied_noDefaultPresent() {
		final ColorStateListHandlerTestTarget target = new WithNoDefault(context);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>none()));
	}
}