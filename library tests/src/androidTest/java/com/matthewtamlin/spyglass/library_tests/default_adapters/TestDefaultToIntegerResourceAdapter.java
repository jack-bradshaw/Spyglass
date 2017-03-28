package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToIntegerResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library_tests.R.integer.test_integer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToIntegerResourceAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToIntegerResource,
		DefaultToIntegerResourceAdapter> {

	private Context context;

	private Integer expectedDefaultValue;

	private DefaultToIntegerResourceAdapter adapter;

	private DefaultToIntegerResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = context.getResources().getInteger(test_integer);
		adapter = new DefaultToIntegerResourceAdapter();

		annotation = mock(DefaultToIntegerResource.class);
		when(annotation.value()).thenReturn(test_integer);

	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public Integer getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToIntegerResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToIntegerResource getAnnotation() {
		return annotation;
	}
}