package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library_tests.R.dimen.test_dimen;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToDimensionResourceAdapter extends TestDefaultAdapter<
		Float,
		DefaultToDimensionResource,
		DefaultToDimensionResourceAdapter> {

	private Context context;

	private Float expectedDefaultValue;

	private DefaultToDimensionResourceAdapter adapter;

	private DefaultToDimensionResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = context.getResources().getDimension(test_dimen);
		adapter = new DefaultToDimensionResourceAdapter();

		annotation = mock(DefaultToDimensionResource.class);
		when(annotation.value()).thenReturn(test_dimen);
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
	public DefaultToDimensionResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToDimensionResource getAnnotation() {
		return annotation;
	}
}