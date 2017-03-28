package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;

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
		expectedDefaultValue = Color.RED;
		annotation = mock(DefaultToColorResource.class);
		adapter = new DefaultToColorResourceAdapter();
		context = mock(Context.class);

		final int resId = new Random().nextInt(Integer.MAX_VALUE);
		final Resources mockResources = mock(Resources.class);

		when(context.getResources()).thenReturn(mockResources);
		when(mockResources.getColor(resId)).thenReturn(expectedDefaultValue);
		when(annotation.value()).thenReturn(resId);
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