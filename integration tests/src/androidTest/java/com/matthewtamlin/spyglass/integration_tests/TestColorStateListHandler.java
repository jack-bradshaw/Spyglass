package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.color_state_list_handler.ColorStateListHandlerTestTargetBase;
import com.matthewtamlin.spyglass.integration_tests.color_state_list_handler.WithDefaultToColorStateListResource;
import com.matthewtamlin.spyglass.integration_tests.color_state_list_handler.WithDefaultToNull;
import com.matthewtamlin.spyglass.integration_tests.color_state_list_handler.WithoutDefault;
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
public class TestColorStateListHandler {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributePresent() {
		final AttributeSet attrs = fromXml(context, R.xml.color_state_list_handler_with_attr_equals_main_csl);

		final ColorStateListHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		final ColorStateList expectedValue = ContextCompat.getColorStateList(
				context,
				R.color.main_color_state_list_for_testing);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassNeverCallsMethod_attributeMissing_noDefaultPresent() {
		final AttributeSet attrs = fromXml(context, R.xml.color_state_list_handler_without_attr);

		final ColorStateListHandlerTestTargetBase target = new WithoutDefault(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesCorrectData_attributeMissing_defaultToColorStateListPresent() {
		final AttributeSet attrs = fromXml(context, R.xml.color_state_list_handler_without_attr);

		final ColorStateListHandlerTestTargetBase target = new WithDefaultToColorStateListResource(context, attrs);

		final ColorStateList expectedValue = ContextCompat.getColorStateList(
				context,
				R.color.default_color_state_list_for_testing);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesDataCorrectly_noAttributesSupplied_defaultToColorStateListPresent() {
		final ColorStateListHandlerTestTargetBase target = new WithDefaultToColorStateListResource(context);

		final ColorStateList expectedValue = ContextCompat.getColorStateList(
				context,
				R.color.default_color_state_list_for_testing);

		assertThat(target.getReceivedValue(), is(ReceivedValue.of(expectedValue)));
	}

	@Test
	@UiThreadTest
	public void testSpyglassPassesDataCorrectly_noAttributesSupplied_noDefaultPresent() {
		final ColorStateListHandlerTestTargetBase target = new WithoutDefault(context);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>none()));
	}

	@Test
	@UiThreadTest
	public void testSpyglassCallsMethod_attributeMissing_defaultToNullPresent() {
		final AttributeSet attrs = AttributeSetSupplier.fromXml(context, R.xml.color_state_list_handler_without_attr);

		final ColorStateListHandlerTestTargetBase target = new WithDefaultToNull(context, attrs);

		assertThat(target.getReceivedValue(), is(ReceivedValue.<ColorStateList>of(null)));
	}
}