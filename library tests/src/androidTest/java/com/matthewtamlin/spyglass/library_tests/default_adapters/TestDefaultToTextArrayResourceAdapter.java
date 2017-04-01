package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToTextArrayResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextArrayResource;

import org.junit.Before;

import static com.matthewtamlin.spyglass.library_tests.R.array.test_string_array;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDefaultToTextArrayResourceAdapter extends TestDefaultAdapter<
		CharSequence[],
		DefaultToTextArrayResource,
		DefaultToTextArrayResourceAdapter> {

	private Context context;

	private CharSequence[] expectedDefaultValue;

	private DefaultToTextArrayResourceAdapter adapter;

	private DefaultToTextArrayResource annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = context.getResources().getTextArray(test_string_array);
		adapter = new DefaultToTextArrayResourceAdapter();

		annotation = mock(DefaultToTextArrayResource.class);
		when(annotation.value()).thenReturn(test_string_array);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public CharSequence[] getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToTextArrayResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToTextArrayResource getAnnotation() {
		return annotation;
	}
}