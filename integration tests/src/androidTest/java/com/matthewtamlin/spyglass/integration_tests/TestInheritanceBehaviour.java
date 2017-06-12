package com.matthewtamlin.spyglass.integration_tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.integration_tests.inheritance_behaviour_test_target.Subclass;
import com.matthewtamlin.spyglass.integration_tests.inheritance_behaviour_test_target.Superclass;
import com.matthewtamlin.spyglass.integration_tests.testing_utilities.SynchronousUiThreadExecutor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(AndroidJUnit4.class)
public class TestInheritanceBehaviour {
	@Rule
	public final ActivityTestRule<EmptyActivity> activityRule = new ActivityTestRule<>(EmptyActivity.class);

	private SynchronousUiThreadExecutor executor;

	@Before
	public void setup() {
		executor = new SynchronousUiThreadExecutor(activityRule.getActivity());
	}

	@Test
	public void testSuperclassInstantiationTriggersSuperclassSpyglass() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Superclass s = new Superclass(activityRule.getActivity());

				final List<Object> expectedInvocationArgs = s.getSuperclassExpectedInvocationArgs();

				assertThat("Spyglass didn't pass a value.",
						s.getSuperclassActualInvocationArgs(),
						is(expectedInvocationArgs == null ? nullValue() : expectedInvocationArgs));
			}
		});
	}

	@Test
	public void testSubclassInstantiationTriggersSuperclassSpyglass() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Subclass s = new Subclass(activityRule.getActivity());

				final List<Object> expectedInvocationArgs = s.getSuperclassExpectedInvocationArgs();

				assertThat("Spyglass didn't pass a value.",
						s.getSuperclassActualInvocationArgs(),
						is(expectedInvocationArgs == null ? nullValue() : expectedInvocationArgs));
			}
		});
	}

	@Test
	public void testSubclassInstantiationTriggersSubclassSpyglass() {
		executor.execute(new Runnable() {
			@Override
			public void run() {
				final Subclass s = new Subclass(activityRule.getActivity());

				final List<Object> expectedInvocationArgs = s.getSubclassExpectedInvocationArgs();

				assertThat("Spyglass didn't pass a value.",
						s.getSubclassActualInvocationArgs(),
						is(expectedInvocationArgs == null ? nullValue() : expectedInvocationArgs));
			}
		});
	}
}