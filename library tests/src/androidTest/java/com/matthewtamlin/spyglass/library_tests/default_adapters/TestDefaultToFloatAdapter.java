package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFloatAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFloat;

import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDefaultToFloatAdapter extends TestDefaultAdapter<
		Float,
		DefaultToFloat,
		DefaultToFloatAdapter> {

	private Context context;

	private Float expectedDefaultValue;

	private DefaultToFloatAdapter adapter;

	private DefaultToFloat annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = Float.MAX_VALUE;
		adapter = new DefaultToFloatAdapter();

		annotation = mock(DefaultToFloat.class);
		when(annotation.value()).thenReturn(expectedDefaultValue);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public Float getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToFloatAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToFloat getAnnotation() {
		return annotation;
	}
}