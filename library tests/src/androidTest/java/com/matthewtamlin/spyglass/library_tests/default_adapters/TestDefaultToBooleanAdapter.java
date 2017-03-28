package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToBooleanAdapter extends TestDefaultAdapter<
		Boolean,
		DefaultToBoolean,
		DefaultToBooleanAdapter> {

	private Context context;

	private Boolean expectedDefaultValue;

	private DefaultToBooleanAdapter adapter;

	private DefaultToBoolean annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = Boolean.TRUE;
		adapter = new DefaultToBooleanAdapter();

		annotation = mock(DefaultToBoolean.class);
		when(annotation.value()).thenReturn(expectedDefaultValue);
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
	public DefaultToBooleanAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToBoolean getAnnotation() {
		return annotation;
	}
}