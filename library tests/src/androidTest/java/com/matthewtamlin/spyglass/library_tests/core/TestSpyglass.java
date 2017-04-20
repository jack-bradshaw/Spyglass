package com.matthewtamlin.spyglass.library_tests.core;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import com.matthewtamlin.spyglass.library.core.IllegalThreadException;
import com.matthewtamlin.spyglass.library.core.Spyglass;
import com.matthewtamlin.spyglass.library.core.SpyglassMethodCallException;
import com.matthewtamlin.spyglass.library_tests.activity.EmptyActivity;

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
import static com.matthewtamlin.spyglass.library_tests.R.attr.SpyglassTestDefStyleAttr;
import static com.matthewtamlin.spyglass.library_tests.R.string.test_string;
import static com.matthewtamlin.spyglass.library_tests.R.style.ThemeWithTestString;
import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView;
import static com.matthewtamlin.spyglass.library_tests.R.xml.no_attrs;
import static com.matthewtamlin.spyglass.library_tests.R.xml.with_string_attr;
import static com.matthewtamlin.spyglass.library_tests.core.TargetViews.DEFAULT_STRING;
import static com.matthewtamlin.spyglass.library_tests.core.TargetViews.USE_BYTE_VALUE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Rule
	public ActivityTestRule<EmptyActivity> activityRule =
			new ActivityTestRule<>(EmptyActivity.class);

	private Context context;

	private String testString;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		testString = context.getString(test_string);
	}

	@Test(expected = IllegalThreadException.class)
	public void testPassDataToMethods_calledOnNonUiThread() {
		final Spyglass spyglass = Spyglass.builder()
				.withView(mock(View.class))
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
		final TargetViews.NoAnnotations view =
				new TargetViews.NoAnnotations(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(nullValue()));
	}

	@Test
	public void testPassDataToMethods_onlyHandlerPresent_attrPresent() {
		final TargetViews.OnlyHandlerPresent view =
				new TargetViews.OnlyHandlerPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getArgsFromLastSpyglassMethodInvocation(),
				is(new Object[]{testString, USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_onlyHandlerPresent_attrMissing() {
		final TargetViews.OnlyHandlerPresent view =
				new TargetViews.OnlyHandlerPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(nullValue()));
	}

	@Test
	public void testPassDataToMethods_handlerAndDefaultPresent_attrPresent() {
		final TargetViews.HandlerAndDefaultPresent view =
				new TargetViews.HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getArgsFromLastSpyglassMethodInvocation(),
				is(new Object[]{testString, USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_handlerAndDefaultPresent_attrMissing() {
		final TargetViews.HandlerAndDefaultPresent view =
				new TargetViews.HandlerAndDefaultPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getArgsFromLastSpyglassMethodInvocation(),
				is(new Object[]{DEFAULT_STRING, USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_attributesOverriddenByDefStyleAttr() {
		final TargetViews.OnlyHandlerPresent view =
				new TargetViews.OnlyHandlerPresent(context);

		// Use activity not context, since activity has the required theme
		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(activityRule.getActivity())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.withDefStyleAttr(SpyglassTestDefStyleAttr)
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getArgsFromLastSpyglassMethodInvocation(),
				is(new Object[]{testString, USE_BYTE_VALUE}));
	}

	@Test
	public void testPassDataToMethods_attributesOverriddenByDefStyleRes() {
		final TargetViews.OnlyHandlerPresent view =
				new TargetViews.OnlyHandlerPresent(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.withDefStyleRes(ThemeWithTestString)
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(
				view.getArgsFromLastSpyglassMethodInvocation(),
				is(new Object[]{testString, USE_BYTE_VALUE}));
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_handlerTypeMismatch() {
		final TargetViews.HandlerTypeMismatch view =
				new TargetViews.HandlerTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_defaultTypeMismatch() {
		final TargetViews.DefaultTypeMismatch view =
				new TargetViews.DefaultTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_useTypeMismatch() {
		final TargetViews.UseTypeMismatch view =
				new TargetViews.UseTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
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