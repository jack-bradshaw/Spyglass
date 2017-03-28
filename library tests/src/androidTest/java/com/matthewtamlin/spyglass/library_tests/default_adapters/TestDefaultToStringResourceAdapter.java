package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToStringResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library_tests.R.string.test_string;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToStringResourceAdapter extends TestDefaultAdapter<
		String,
		DefaultToStringResource,
		DefaultToStringResourceAdapter> {

	private Context context;

	private String expectedDefaultValue;

	private DefaultToStringResourceAdapter adapter;

	private DefaultToStringResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = context.getResources().getString(test_string);
		adapter = new DefaultToStringResourceAdapter();

		annotation = mock(DefaultToStringResource.class);
		when(annotation.value()).thenReturn(test_string);

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
	public DefaultToStringResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToStringResource getAnnotation() {
		return annotation;
	}
}