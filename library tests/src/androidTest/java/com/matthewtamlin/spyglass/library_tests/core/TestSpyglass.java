package com.matthewtamlin.spyglass.library_tests.core;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;

import com.matthewtamlin.spyglass.library.core.IllegalThreadException;
import com.matthewtamlin.spyglass.library.core.MandatoryAttributeMissingException;
import com.matthewtamlin.spyglass.library.core.Spyglass;
import com.matthewtamlin.spyglass.library.core.SpyglassFieldBindException;
import com.matthewtamlin.spyglass.library.core.SpyglassMethodCallException;
import com.matthewtamlin.spyglass.library_tests.views.SpyglassTestViewsFieldVariants;
import com.matthewtamlin.spyglass.library_tests.views.SpyglassTestViewsMethodVariants;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlPullParser;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static android.support.test.InstrumentationRegistry.getContext;
import static com.matthewtamlin.spyglass.library_tests.R.string.spyglass_test_string;
import static com.matthewtamlin.spyglass.library_tests.R.string.test_string;
import static com.matthewtamlin.spyglass.library_tests.R.styleable.SpyglassTestView;
import static com.matthewtamlin.spyglass.library_tests.R.xml.no_attrs;
import static com.matthewtamlin.spyglass.library_tests.R.xml.with_string_attr;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class)
public class TestSpyglass {
	@Test(expected = IllegalThreadException.class)
	public void testBindDataToFields_calledOnNonUiThread() {
		final Spyglass spyglass = Spyglass.builder()
				.withView(mock(View.class))
				.withContext(getContext())
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
				mock(SpyglassTestViewsFieldVariants.NoAnnotations.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.bindDataToFields();
	}

	@Test
	public void testBindDataToFields_attrSupplied() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault view =
				mock(SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		spyglass.bindDataToFields();

		assertThat(view.spyglassField, is(getContext().getString(spyglass_test_string)));
	}

	@Test
	public void testBindDataToFields_attrMissing_noDefault_notMandatory() {
		final SpyglassTestViewsFieldVariants.OptionalStringHandlerNoDefault view =
				mock(SpyglassTestViewsFieldVariants.OptionalStringHandlerNoDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.bindDataToFields();

		assertThat(view.spyglassField, is(SpyglassTestViewsFieldVariants.INITIAL_STRING));
	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testBindDataToFields_attrMissing_noDefault_isMandatory() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault view =
				mock(SpyglassTestViewsFieldVariants.MandatoryStringHandlerNoDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.bindDataToFields();
	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_notMandatory() {
		final SpyglassTestViewsFieldVariants.OptionalStringHandlerWithDefault view =
				mock(SpyglassTestViewsFieldVariants.OptionalStringHandlerWithDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.bindDataToFields();

		assertThat(view.spyglassField, is(SpyglassTestViewsFieldVariants.DEFAULT_STRING));
	}

	@Test
	public void testBindDataToFields_attrMissing_hasDefault_isMandatory() {
		final SpyglassTestViewsFieldVariants.MandatoryStringHandlerWithDefault view =
				mock(SpyglassTestViewsFieldVariants.MandatoryStringHandlerWithDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.bindDataToFields();

		assertThat(view.spyglassField, is(SpyglassTestViewsFieldVariants.DEFAULT_STRING));
	}

	@Test(expected = SpyglassFieldBindException.class)
	public void testBindDataToFields_dataTypeMismatch() {
		final SpyglassTestViewsFieldVariants.HandlerTypeMismatch view =
				mock(SpyglassTestViewsFieldVariants.HandlerTypeMismatch.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		spyglass.bindDataToFields();
	}

	@Test(expected = IllegalThreadException.class)
	public void testPassDataToMethods_calledOnNonUiThread() {
		final Spyglass spyglass = Spyglass.builder()
				.withView(mock(View.class))
				.withContext(getContext())
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
				mock(SpyglassTestViewsMethodVariants.NoAnnotations.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		spyglass.passDataToMethods();

		verify(view, never()).spyglassMethod(anyString(), anyByte());
	}

	@Test
	public void testPassDataToMethods_attrSupplied() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault view =
				mock(SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(with_string_attr))
				.build();

		spyglass.passDataToMethods();

		final String expectedString = getContext().getString(test_string);
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;

		verify(view, times(1)).spyglassMethod(eq(expectedString), eq(expectedByte));
	}

	@Test
	public void testPassDataToMethods_attrMissing_noDefault_notMandatory() {
		final SpyglassTestViewsMethodVariants.OptionalStringHandlerNoDefault view =
				mock(SpyglassTestViewsMethodVariants.OptionalStringHandlerNoDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.passDataToMethods();

		verify(view, never()).spyglassMethod(anyString(), anyByte());
	}

	@Test(expected = MandatoryAttributeMissingException.class)
	public void testPassDataToMethods_attrMissing_noDefault_isMandatory() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault view =
				mock(SpyglassTestViewsMethodVariants.MandatoryStringHandlerNoDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.passDataToMethods();
	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_notMandatory() {
		final SpyglassTestViewsMethodVariants.OptionalStringHandlerWithDefault view = mock
				(SpyglassTestViewsMethodVariants.OptionalStringHandlerWithDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.passDataToMethods();

		final String expectedString = SpyglassTestViewsMethodVariants.DEFAULT_STRING;
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;

		verify(view, times(1)).spyglassMethod(eq(expectedString), eq(expectedByte));
	}

	@Test
	public void testPassDataToMethods_attrMissing_hasDefault_isMandatory() {
		final SpyglassTestViewsMethodVariants.MandatoryStringHandlerWithDefault view = mock
				(SpyglassTestViewsMethodVariants.MandatoryStringHandlerWithDefault.class);

		final Spyglass spyglass = Spyglass.builder()
				.withView(view)
				.withContext(getContext())
				.withStyleableResource(SpyglassTestView)
				.withAttributeSet(getAttrSetFromXml(no_attrs))
				.build();

		spyglass.passDataToMethods();

		final String expectedString = SpyglassTestViewsMethodVariants.DEFAULT_STRING;
		final byte expectedByte = SpyglassTestViewsMethodVariants.USE_BYTE_VALUE;

		verify(view, times(1)).spyglassMethod(eq(expectedString), eq(expectedByte));
	}

	@Test(expected = SpyglassMethodCallException.class)
	public void testPassDataToMethods_dataTypeMismatch() {

	}

	private AttributeSet getAttrSetFromXml(final int xmlResId) {
		final Context context = InstrumentationRegistry.getTargetContext();

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
}