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

	private Boolean expectedDefaultValue;

	private DefaultToBoolean annotation;

	private DefaultToBooleanAdapter adapter;

	private Context context;

	@Before
	public void setup() {
		expectedDefaultValue = Boolean.TRUE;
		annotation = mock(DefaultToBoolean.class);
		adapter = new DefaultToBooleanAdapter();
		context = mock(Context.class);

		when(annotation.value()).thenReturn(expectedDefaultValue);
	}

	@Override
	public Boolean getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToBoolean getAnnotation() {
		return annotation;
	}

	@Override
	public DefaultToBooleanAdapter getAdapter() {
		return adapter;
	}

	@Override
	public Context getContext() {
		return context;
	}
}