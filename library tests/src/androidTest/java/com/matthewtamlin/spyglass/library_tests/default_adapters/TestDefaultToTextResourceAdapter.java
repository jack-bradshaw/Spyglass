package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToTextArrayResourceAdapter;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToTextResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextResource;

import org.junit.Before;

import static com.matthewtamlin.spyglass.library_tests.R.string.test_string;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDefaultToTextResourceAdapter extends TestDefaultAdapter<
		CharSequence,
		DefaultToTextResource,
		DefaultToTextResourceAdapter> {

	private Context context;

	private CharSequence expectedDefaultValue;

	private DefaultToTextResourceAdapter adapter;

	private DefaultToTextResource annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = context.getResources().getText(test_string);
		adapter = new DefaultToTextResourceAdapter();

		annotation = mock(DefaultToTextResource.class);
		when(annotation.value()).thenReturn(test_string);
	}

	@Override
	public Context getContext() {
		return context;
	}

	@Override
	public CharSequence getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToTextResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToTextResource getAnnotation() {
		return annotation;
	}
}