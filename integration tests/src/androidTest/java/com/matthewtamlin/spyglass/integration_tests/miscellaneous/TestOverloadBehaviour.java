package com.matthewtamlin.spyglass.integration_tests.miscellaneous;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.test.rule.UiThreadTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;

import com.matthewtamlin.spyglass.integration_tests.R;
import com.matthewtamlin.spyglass.integration_tests.framework.AttributeSetSupplier;
import com.matthewtamlin.spyglass.integration_tests.framework.ReceivedValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(AndroidJUnit4.class)
public class TestOverloadBehaviour {
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
		final AttributeSet attrs = AttributeSetSupplier.fromXml(
				context,
				R.xml.overload_behaviour_test_target_with_attr_equals_hello);

		final OverloadBehaviourTestTarget target = new OverloadBehaviourTestTarget(context, attrs);

		assertThat(target.getPositiveReceivedValue(), is(ReceivedValue.of("hello")));
		assertThat(target.getNegativeReceivedValue(), is(ReceivedValue.<String>none()));
	}
}