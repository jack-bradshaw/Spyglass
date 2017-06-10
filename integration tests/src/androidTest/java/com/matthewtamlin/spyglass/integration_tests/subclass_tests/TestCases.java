package com.matthewtamlin.spyglass.integration_tests.subclass_tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.integration_tests.EmptyActivity;
import com.matthewtamlin.spyglass.integration_tests.testing_utilities.SynchronousUiThreadExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class TestCases {
	@Rule
	public final ActivityTestRule<EmptyActivity> activityRule = new ActivityTestRule<>(EmptyActivity.class);

	private SynchronousUiThreadExecutor executor;

	@Before
	public void setup() {
		executor = new SynchronousUiThreadExecutor(activityRule.getActivity());
	}

	@Test
	public void testSubclassInstantiationTriggersSuperclassSpyglass() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Subclass s = new Subclass(activityRule.getActivity());

				assertThat("Spyglass didn't pass a value.", s.valueHasBeenReceived(), is(true));
				assertThat("Spyglass passed the wrong value.", s.getReceivedValue(), is(Superclass.EXPECTED_VALUE));
			}
		});
	}
}