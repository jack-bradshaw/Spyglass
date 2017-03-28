package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFractionResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library_tests.R.fraction.test_frac;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToFractionResourceAdapter extends TestDefaultAdapter<
		Float,
		DefaultToFractionResource,
		DefaultToFractionResourceAdapter> {

	private Context context;

	private Float expectedDefaultValue;

	private DefaultToFractionResourceAdapter adapter;

	private DefaultToFractionResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = context.getResources().getFraction(test_frac, 2, 10);
		adapter = new DefaultToFractionResourceAdapter();

		annotation = mock(DefaultToFractionResource.class);
		when(annotation.resId()).thenReturn(test_frac);
		when(annotation.baseMultiplier()).thenReturn(2);
		when(annotation.parentMultiplier()).thenReturn(10);
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
	public DefaultToFractionResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToFractionResource getAnnotation() {
		return annotation;
	}
}