package com.matthewtamlin.spyglass.integration_tests.exception_behaviour_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;

import com.matthewtamlin.spyglass.core.TargetException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class TestExceptionBehaviour {
	@Rule
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testExceptionBehaviour_handlerMethodThrowsThrowable() {
		try {
			new ThrowsThrowable(context);
		} catch (final TargetException e) {
			assertThat(e.getCause(), is(ThrowsThrowable.getExpectedThrowable()));

			return;
		}

		assertThat("No exception thrown.", false);
	}
}