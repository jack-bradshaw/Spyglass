package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToTextResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library_tests.R.string.test_string;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
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
		context = InstrumentationRegistry.getTargetContext();
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