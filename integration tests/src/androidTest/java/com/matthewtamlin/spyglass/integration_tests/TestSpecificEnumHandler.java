package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.testing_utilities.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integration_tests.testing_utilities.SynchronousUiThreadExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestSpecificEnumHandler {
	@Rule
	public final ActivityTestRule<EmptyActivity> activityRule = new ActivityTestRule<>(EmptyActivity.class);

	private SynchronousUiThreadExecutor executor;

	@Before
	public void setup() {
		executor = new SynchronousUiThreadExecutor(activityRule.getActivity());
	}

	@Test
	public void testSpyglassCallsMethod_attributePresentAndCorrectOrdinal() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Context context = activityRule.getActivity();
				final AttributeSet attrs = AttributeSetSupplier.fromXml(
						context,
						R.xml.specific_enum_handler_with_attr_correct_ordinal);

				final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context, attrs);

				assertThat(target.wasHandlerCalled(), is(true));
			}
		});
	}

	@Test
	public void testSpyglassNeverCallsMethod_attributePresentButIncorrectOrdinal() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Context context = activityRule.getActivity();
				final AttributeSet attrs = AttributeSetSupplier.fromXml(
						context,
						R.xml.specific_enum_handler_with_attr_incorrect_ordinal);

				final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context, attrs);

				assertThat(target.wasHandlerCalled(), is(false));
			}
		});
	}

	@Test
	public void testSpyglassNeverCallsMethod_attributeMissing() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Context context = activityRule.getActivity();
				final AttributeSet attrs = AttributeSetSupplier.fromXml(
						context,
						R.xml.specific_enum_handler_without_attr);

				final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context, attrs);

				assertThat(target.wasHandlerCalled(), is(false));
			}
		});
	}

	@Test
	public void testSpyglassNeverCallsMethod_noAttributesSupplied() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Context context = activityRule.getActivity();

				final SpecificEnumHandlerTestTarget target = new SpecificEnumHandlerTestTarget(context);

				assertThat(target.wasHandlerCalled(), is(false));
			}
		});
	}
}