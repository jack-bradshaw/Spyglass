package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library_tests.R.color.test_color;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToColorResourceAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToColorResource,
		DefaultToColorResourceAdapter> {

	private Context context;

	private Integer expectedDefaultValue;

	private DefaultToColorResourceAdapter adapter;

	private DefaultToColorResource annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = ContextCompat.getColor(context, test_color);
		adapter = new DefaultToColorResourceAdapter();

		annotation = mock(DefaultToColorResource.class);
		when(annotation.value()).thenReturn(test_color);
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
	public DefaultToColorResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToColorResource getAnnotation() {
		return annotation;
	}
}