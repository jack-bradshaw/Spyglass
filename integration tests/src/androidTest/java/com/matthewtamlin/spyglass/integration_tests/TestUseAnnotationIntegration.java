package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.integration_tests.testing_utilities.SynchronousUiThreadExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestUseAnnotationIntegration {
	@Rule
	public final ActivityTestRule<EmptyActivity> activityRule = new ActivityTestRule<>(EmptyActivity.class);

	private SynchronousUiThreadExecutor executor;

	@Before
	public void setup() {
		executor = new SynchronousUiThreadExecutor(activityRule.getActivity());
	}

	@Test
	public void testUseAnnotationsPassCorrectValues() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Context context = activityRule.getActivity();
				final UseAnnotationIntegrationTestTarget target = new UseAnnotationIntegrationTestTarget(context);

				final List<Object> expectedInvocationArgs = target.getExpectedInvocationArgs();

				assertThat(
						target.getActualInvocationArgs(),
						is(expectedInvocationArgs == null ? nullValue() : expectedInvocationArgs));
			}
		});
	}
}