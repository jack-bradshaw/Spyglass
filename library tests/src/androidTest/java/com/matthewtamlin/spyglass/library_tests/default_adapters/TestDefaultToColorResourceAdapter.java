package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.library_tests.R;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToColorResourceAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToColorResource,
		DefaultToColorResourceAdapter> {

	private Integer expectedDefaultValue;

	private DefaultToColorResource annotation;

	private DefaultToColorResourceAdapter adapter;

	private Context context;

	@Before
	public void setup() {
		annotation = mock(DefaultToColorResource.class);
		adapter = new DefaultToColorResourceAdapter();
		context = InstrumentationRegistry.getTargetContext();
		expectedDefaultValue = ContextCompat.getColor(context, R.color.test_color);

		when(annotation.value()).thenReturn(R.color.test_color);
	}

	@Override
	public Integer getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToColorResource getAnnotation() {
		return annotation;
	}

	@Override
	public DefaultToColorResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public Context getContext() {
		return context;
	}
}