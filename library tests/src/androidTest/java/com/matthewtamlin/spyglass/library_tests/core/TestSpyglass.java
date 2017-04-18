package com.matthewtamlin.spyglass.library_tests.core;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import com.matthewtamlin.spyglass.library.core.IllegalThreadException;
import com.matthewtamlin.spyglass.library.core.MandatoryAttributeMissingException;
import com.matthewtamlin.spyglass.library.core.Spyglass;
import com.matthewtamlin.spyglass.library.core.SpyglassFieldBindException;
import com.matthewtamlin.spyglass.library.core.SpyglassMethodCallException;
import com.matthewtamlin.spyglass.library_tests.R;
import com.matthewtamlin.spyglass.library_tests.activity.EmptyActivity;
import com.matthewtamlin.spyglass.library_tests.views.SpyglassTestViewsFieldVariants;
import com.matthewtamlin.spyglass.library_tests.views.SpyglassTestViewsMethodVariants;

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
import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView;
import static com.matthewtamlin.spyglass.library_tests.R.xml.no_attrs;
import static com.matthewtamlin.spyglass.library_tests.R.xml.with_string_attr;
import static com.matthewtamlin.spyglass.library_tests.views.SpyglassTestViewsFieldVariants.INITIAL_STRING;
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
	public void testBindDataToFields_calledOnNonUiThread() {
		final Spyglass spyglass = Spyglass.builder()
				.withView(mock(View.class))
				.withContext(context)
				.withStyleableResource(new int[0])
				.build();

		final Future<Void> f = Executors.newSingleThreadExecutor().submit(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				// This should fail
				spyglass.bindDataToFields();
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
	public void testBindDataToFields_noAnnotations() {
		final SpyglassTestViewsFieldVariants.NoAnnotations view =
				new SpyglassTestViewsFieldVariants.NoAnnotations(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		bindDataToFieldsSynchronously(spyglass);

		assertThat(view.spyglassField, is(INITIAL_STRING));
	}

	@Test
	public void testBindDataToFields_attrSupplied() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		bindDataToFieldsSynchronously(spyglass);

		assertThat(view.spyglassField, is(testString));
	}

	@Test
	public void testBindDataToFields_attrMissing_noDefault_notMandatory() {
		final SpyglassTestViewsFieldVariants.OptionalStringHandlerNoDefault view =
				new SpyglassTestViewsFieldVariants.OptionalStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		bindDataToFieldsSynchronously(spyglass);

		assertThat(view.spyglassField, is(INITIAL_STRING));
	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testBindDataToFields_attrMissing_noDefault_isMandatory() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		bindDataToFieldsSynchronously(spyglass);
	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_notMandatory() {
		final SpyglassTestViewsFieldVariants.OptionalStringHandlerWithDefault view =
				new SpyglassTestViewsFieldVariants.OptionalStringHandlerWithDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		bindDataToFieldsSynchronously(spyglass);

		assertThat(view.spyglassField, is(SpyglassTestViewsFieldVariants.DEFAULT_STRING));
	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_isMandatory() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerWithDefault view =
				new SpyglassTestViewsFieldVariants.MandatoryStringHandlerWithDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		bindDataToFieldsSynchronously(spyglass);

		assertThat(view.spyglassField, is(SpyglassTestViewsFieldVariants.DEFAULT_STRING));
	}

	@Test
	public void testBindDataToFields_attributesOverriddenByDefStyleAttr() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault(context);

		// Use activity not context, since activity has the required theme
		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(activityRule.getActivity())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.withDefStyleAttr(SpyglassTestDefStyleAttr)
				.build();

		bindDataToFieldsSynchronously(spyglass);

		assertThat(view.spyglassField, is(testString));
	}

	@Test
	public void testBindDataToFields_attributesOverriddenByDefStyleRes() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.withDefStyleRes(R.style.ThemeWithTestString)
				.build();

		bindDataToFieldsSynchronously(spyglass);

		assertThat(view.spyglassField, is(testString));
	}

	@Test(expected = SpyglassFieldBindException.class)
	public void testBindDataToFields_handlerTypeMismatch() {
		final SpyglassTestViewsFieldVariants.HandlerTypeMismatch view =
				new SpyglassTestViewsFieldVariants.HandlerTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		bindDataToFieldsSynchronously(spyglass);
	}

	@Test(expected = SpyglassFieldBindException.class)
	public void testBindDataToFields_defaultTypeMismatch() {
		final SpyglassTestViewsFieldVariants.DefaultTypeMismatch view =
				new SpyglassTestViewsFieldVariants.DefaultTypeMismatch(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		bindDataToFieldsSynchronously(spyglass);
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
		final SpyglassTestViewsMethodVariants.NoAnnotations view =
				new SpyglassTestViewsMethodVariants.NoAnnotations(context);

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
	public void testPassDataToMethods_attrSupplied() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		passDataToMethodsSynchronously(spyglass);

		final String expectedString = testString;
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;
		final Object[] expectedArgs = new Object[]{expectedString, expectedByte};

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(expectedArgs));
	}

	@Test
	public void testPassDataToMethods_attrMissing_noDefault_notMandatory() {
		final SpyglassTestViewsMethodVariants.OptionalStringHandlerNoDefault view =
				new SpyglassTestViewsMethodVariants.OptionalStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(nullValue()));
	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testPassDataToMethods_attrMissing_noDefault_isMandatory() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);
	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_notMandatory() {
		final SpyglassTestViewsMethodVariants.OptionalStringHandlerWithDefault view =
				new SpyglassTestViewsMethodVariants.OptionalStringHandlerWithDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);

		final String expectedString = SpyglassTestViewsMethodVariants.DEFAULT_STRING;
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;
		final Object[] expectedArgs = new Object[]{expectedString, expectedByte};

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(expectedArgs));
	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_isMandatory() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerWithDefault view =
				new SpyglassTestViewsMethodVariants.MandatoryStringHandlerWithDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		passDataToMethodsSynchronously(spyglass);

		final String expectedString = SpyglassTestViewsMethodVariants.DEFAULT_STRING;
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;
		final Object[] expectedArgs = new Object[]{expectedString, expectedByte};

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(expectedArgs));
	}

	@Test
	public void testPassDataToMethods_attributesOverriddenByDefStyleAttr() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault(context);

		// Use activity not context, since activity has the required theme
		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(activityRule.getActivity())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.withDefStyleAttr(SpyglassTestDefStyleAttr)
				.build();

		passDataToMethodsSynchronously(spyglass);

		final String expectedString = testString;
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;
		final Object[] expectedArgs = new Object[]{expectedString, expectedByte};

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(expectedArgs));
	}

	@Test
	public void testPassDataToMethods_attributesOverriddenByDefStyleRes() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault view =
				new SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault(context);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(context)
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.withDefStyleRes(R.style.ThemeWithTestString)
				.build();

		passDataToMethodsSynchronously(spyglass);

		final String expectedString = testString;
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;
		final Object[] expectedArgs = new Object[]{expectedString, expectedByte};

		assertThat(view.getArgsFromLastSpyglassMethodInvocation(), is(expectedArgs));
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_handlerTypeMismatch() {
		final SpyglassTestViewsMethodVariants.HandlerTypeMismatch view =
				new SpyglassTestViewsMethodVariants.HandlerTypeMismatch(context);

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
		final SpyglassTestViewsMethodVariants.DefaultTypeMismatch view =
				new SpyglassTestViewsMethodVariants.DefaultTypeMismatch(context);

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
		final SpyglassTestViewsMethodVariants.UseTypeMismatch view =
				new SpyglassTestViewsMethodVariants.UseTypeMismatch(context);

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

	private void bindDataToFieldsSynchronously(final Spyglass spyglass) {
		final ThrowableHandlingRunnable<RuntimeException> runnable =
				new ThrowableHandlingRunnable<RuntimeException>() {
					@Override
					public void run() {
						try {
							spyglass.bindDataToFields();
						} catch (final RuntimeException e) {
							setThrowable(e);
						}
					}
				};

		activityRule.getActivity().runOnUiThread(runnable);
		getInstrumentation().waitForIdleSync();

		if (runnable.getThrowable() != null) {
			throw runnable.getThrowable();
		}
	}

	private void passDataToMethodsSynchronously(final Spyglass spyglass) {
		final ThrowableHandlingRunnable<RuntimeException> runnable =
				new ThrowableHandlingRunnable<RuntimeException>() {
					@Override
					public void run() {
						try {
							spyglass.passDataToMethods();
						} catch (final RuntimeException e) {
							setThrowable(e);
						}
					}
				};

		activityRule.getActivity().runOnUiThread(runnable);
		getInstrumentation().waitForIdleSync();

		if (runnable.getThrowable() != null) {
			throw runnable.getThrowable();
		}
	}
}