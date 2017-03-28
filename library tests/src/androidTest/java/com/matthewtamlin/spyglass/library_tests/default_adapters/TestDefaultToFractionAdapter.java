package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFractionAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFraction;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToFractionAdapter extends TestDefaultAdapter<
		Float,
		DefaultToFraction,
		DefaultToFractionAdapter> {

	private Context context;

	private Float expectedDefaultValue;

	private DefaultToFractionAdapter adapter;

	private DefaultToFraction annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = Float.NEGATIVE_INFINITY;
		adapter = new DefaultToFractionAdapter();

		annotation = mock(DefaultToFraction.class);
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
	public DefaultToFractionAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToFraction getAnnotation() {
		return annotation;
	}
}