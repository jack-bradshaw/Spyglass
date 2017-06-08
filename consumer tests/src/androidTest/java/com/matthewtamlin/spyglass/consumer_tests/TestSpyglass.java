package com.matthewtamlin.spyglass.consumer_tests;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import com.matthewtamlin.spyglass.consumer.IllegalThreadException;
import com.matthewtamlin.spyglass.consumer.Spyglass;
import com.matthewtamlin.spyglass.library.core.SpyglassMethodCallException;
import com.matthewtamlin.spyglass.library_tests.activity.TestSpyglassActivity;
import com.matthewtamlin.spyglass.library_tests.core.TargetViews.HandlerAndDefaultPresent;
import com.matthewtamlin.spyglass.library_tests.core.TargetViews.OnlyHandlerPresent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Rule
	public ActivityTestRule<TestSpyglassActivity> activityRule =
			new ActivityTestRule<>(TestSpyglassActivity.class);

	private Context context;

	private String string1;

	private String string2;

	@Before
	public void setup() {
		context = activityRule.getActivity();

		string1 = context.getString(com.matthewtamlin.spyglass.consumer.R.string.test_spyglass_string_1);
		string2 = context.getString(com.matthewtamlin.spyglass.consumer.R.string.test_spyglass_string_2);
	}

	@Test(expected = IllegalThreadException.class)
	public void testPassDataToMethods_calledOnNonUiThread() {
		final Spyglass spyglass = Spyglass.builder()
				.withTarget(mock(View.class))
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		final Future<Void> f = Executors.newSingleThreadExecutor().submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				// This should fail
				spyglass.passDataToMethods();
				return null;
			}
		});

		try {
			// Test must not complete before the background task is done
			f.get();

		} catch (final InterruptedException e) {
			// This is not expected
			throw new RuntimeException("Test failed. Background task interrupted.");

		} catch (final ExecutionException e) {
			// This is expected
			throw (IllegalThreadException) e.getCause();
		}
	}

	@Test
	public void testPassDataToMethods_noAnnotations() {
		final TargetViews.NoAnnotations view = new TargetViews.NoAnnotations(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.with_string_1_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(view.getLastArgs(), is(nullValue()));
	}

	@Test
	public void testPassDataToMethods_onlyHandlerPresent_attrPresent() {
		final OnlyHandlerPresent view = new OnlyHandlerPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.with_string_1_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getLastArgs(),
				is(new Object[]{string1, TargetViews.USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_onlyHandlerPresent_attrMissing() {
		final OnlyHandlerPresent view = new OnlyHandlerPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(view.getLastArgs(), is(nullValue()));
	}

	@Test
	public void testPassDataToMethods_handlerAndDefaultPresent_attrPresent() {
		final HandlerAndDefaultPresent view = new HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.with_string_1_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getLastArgs(),
				is(new Object[]{string1, TargetViews.USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_handlerAndDefaultPresent_attrMissing() {
		final HandlerAndDefaultPresent view = new HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getLastArgs(),
				is(new Object[]{TargetViews.DEFAULT_STRING, TargetViews.USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_attributeSetConflictsWithDefStyleAttr() {
		final HandlerAndDefaultPresent view = new HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.with_string_1_attr))
				.withDefStyleAttr(com.matthewtamlin.spyglass.consumer.R.attr.StyleWithString2)
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getLastArgs(),
				is(new Object[]{string1, TargetViews.USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_attributeSetConflictsWithDefStyleRes() {
		final HandlerAndDefaultPresent view = new HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.with_string_1_attr))
				.withDefStyleRes(com.matthewtamlin.spyglass.consumer.R.style.StyleWithString2)
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getLastArgs(),
				is(new Object[]{string1, TargetViews.USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_defStyleAttrSuppliesMissingAttributes() {
		final HandlerAndDefaultPresent view = new HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.no_attrs))
				.withDefStyleAttr(com.matthewtamlin.spyglass.consumer.R.attr.StyleWithString2)
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getLastArgs(),
				is(new Object[]{string2, TargetViews.USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_defStyleResSuppliesMissingAttributes() {
		final HandlerAndDefaultPresent view = new HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.no_attrs))
				.withDefStyleRes(com.matthewtamlin.spyglass.consumer.R.style.StyleWithString2)
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getLastArgs(),
				is(new Object[]{string2, TargetViews.USE_BYTE_VALUE}));
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_handlerTypeMismatch() {
		final TargetViews.HandlerTypeMismatch view = new TargetViews.HandlerTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.with_string_1_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_defaultTypeMismatch() {
		final TargetViews.DefaultTypeMismatch view = new TargetViews.DefaultTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_useTypeMismatch() {
		final TargetViews.UseTypeMismatch view = new TargetViews.UseTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withTarget(view)
				.withContext(context)
				.withStyleableResource(com.matthewtamlin.spyglass.consumer.R.styleable.SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(com.matthewtamlin.spyglass.consumer.R.xml.with_string_1_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);
	}

	private AttributeSet getAttrSetFromXml(final int xmlResId) {
		final XmlPullParser parser = context.getResources().getXml(xmlResId);

		try {
			for (int type = 0;
				 (type != XmlPullParser.END_DOCUMENT) && (type != XmlPullParser.START_TAG);
				 type = parser.next()) {}
		} catch (final Exception e) {
			throw new RuntimeException("Test aborted. Could not parse XML.", e);
		}

		return Xml.asAttributeSet(parser);
	}

	private void passDataToMethodsSynchronously(final Spyglass spyglass) {
		final RuntimeException[] exceptionHolder = new RuntimeException[1];

		activityRule.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					spyglass.passDataToMethods();
				} catch (final RuntimeException e) {
					exceptionHolder[0] = e;
				}
			}
		});

		getInstrumentation().waitForIdleSync();

		if (exceptionHolder[0] != null) {
			throw exceptionHolder[0];
		}
	}
}