package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.test.InstrumentationRegistry;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDrawableResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;

import org.junit.Before;

import static com.matthewtamlin.spyglass.library_tests.R.drawable.drawable;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDefaultToDrawableResourceAdapter extends TestDefaultAdapter<
		Drawable,
		DefaultToDrawableResource,
		DefaultToDrawableResourceAdapter> {

	private Context context;

	private Drawable expectedDefaultValue;

	private DefaultToDrawableResourceAdapter adapter;

	private DefaultToDrawableResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = ContextCompat.getDrawable(context, drawable);
		adapter = new DefaultToDrawableResourceAdapter();

		annotation = mock(DefaultToDrawableResource.class);
		when(annotation.value()).thenReturn(drawable);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public Drawable getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToDrawableResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToDrawableResource getAnnotation() {
		return annotation;
	}

	/**
	 * Superclass compares objects directly. For bitmap drawables, the constant state must be
	 * compared.
	 */
	@Override
	public void testGetDefault_validArguments() {
		final Drawable defaultValue = adapter.getDefault(annotation, context);

		assertThat(defaultValue.getConstantState(), is(expectedDefaultValue.getConstantState()));
	}
}