package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.library_tests.R;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Random;

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

	private DefaultToBooleanResource annotation;

	private DefaultToBooleanResourceAdapter adapter;

	@Before
	public void setup() {
		annotation = mock(DefaultToBooleanResource.class);
		adapter = new DefaultToBooleanResourceAdapter();
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue =context.getResources().getBoolean(test_bool);

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
	public DefaultToBooleanResource getAnnotation() {
		return annotation;
	}

	@Override
	public DefaultToBooleanResourceAdapter getAdapter() {
		return adapter;
	}
}