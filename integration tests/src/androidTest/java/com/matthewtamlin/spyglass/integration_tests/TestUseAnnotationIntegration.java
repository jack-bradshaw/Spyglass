package com.matthewtamlin.spyglass.integration_tests;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;

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
	public final UiThreadTestRule uiThreadTestRule = new UiThreadTestRule();

	private Context context;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
	}

	@Test
	@UiThreadTest
	public void testUseAnnotationsPassCorrectValues() {
		final UseAnnotationIntegrationTestTarget target = new UseAnnotationIntegrationTestTarget(context);

		final List<Object> expectedInvocationArgs = target.getExpectedInvocationArgs();

		assertThat(
				target.getActualInvocationArgs(),
				is(expectedInvocationArgs == null ? nullValue() : expectedInvocationArgs));
	}
}