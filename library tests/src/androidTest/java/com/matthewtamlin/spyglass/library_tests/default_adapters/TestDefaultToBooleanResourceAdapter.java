package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;

import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToBooleanResourceAdapter extends TestDefaultAdapter<
		Boolean,
		DefaultToBooleanResource,
		DefaultToBooleanResourceAdapter> {

	private Boolean expectedDefaultValue;

	private DefaultToBooleanResource annotation;

	private DefaultToBooleanResourceAdapter adapter;

	private Context context;

	@Before
	public void setup() {
		expectedDefaultValue = Boolean.TRUE;
		annotation = mock(DefaultToBooleanResource.class);
		adapter = new DefaultToBooleanResourceAdapter();
		context = mock(Context.class);

		final int resId = new Random().nextInt(Integer.MAX_VALUE);
		final Resources mockResources = mock(Resources.class);

		when(context.getResources()).thenReturn(mockResources);
		when(mockResources.getBoolean(resId)).thenReturn(expectedDefaultValue);
		when(annotation.value()).thenReturn(resId);
	}

	@Override
	public Boolean getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToBooleanResource getAnnotation() {
		return annotation;
	}

	@Override
	public DefaultToBooleanResourceAdapter getAdapter() {
		return adapter;
	}

	@Override
	public Context getContext() {
		return context;
	}
}