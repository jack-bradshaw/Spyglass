package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToStringAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToStringAdapter extends TestDefaultAdapter<
		String,
		DefaultToString,
		DefaultToStringAdapter> {

	private Context context;

	private String expectedDefaultValue;

	private DefaultToStringAdapter adapter;

	private DefaultToString annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = "Test string 123";
		adapter = new DefaultToStringAdapter();

		annotation = mock(DefaultToString.class);
		when(annotation.value()).thenReturn(expectedDefaultValue);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public String getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToStringAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToString getAnnotation() {
		return annotation;
	}
}