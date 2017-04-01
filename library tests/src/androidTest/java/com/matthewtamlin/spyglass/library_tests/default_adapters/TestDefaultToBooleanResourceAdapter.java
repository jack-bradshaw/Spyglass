package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library_tests.R.bool.test_bool;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToBooleanResourceAdapter extends TestDefaultAdapter<
		Boolean,
		DefaultToBooleanResource,
		DefaultToBooleanResourceAdapter> {

	private Context context;

	private Boolean expectedDefaultValue;

	private DefaultToBooleanResourceAdapter adapter;

	private DefaultToBooleanResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = context.getResources().getBoolean(test_bool);
		adapter = new DefaultToBooleanResourceAdapter();

		annotation = mock(DefaultToBooleanResource.class);
		when(annotation.value()).thenReturn(test_bool);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public Boolean getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToBooleanResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToBooleanResource getAnnotation() {
		return annotation;
	}
}