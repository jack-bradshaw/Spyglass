package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanAdapter;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColor;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToColorAdapter extends TestDefaultAdapter<
		Integer,
		DefaultToColor,
		DefaultToColorAdapter> {

	private Context context;

	private Integer expectedDefaultValue;

	private DefaultToColorAdapter adapter;

	private DefaultToColor annotation;

	@Before
	public void setup() {
		context = mock(Context.class);
		expectedDefaultValue = Color.RED;
		adapter = new DefaultToColorAdapter();

		annotation = mock(DefaultToColor.class);
		when(annotation.value()).thenReturn(expectedDefaultValue);
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
	public DefaultToColorAdapter getAdapter() {
		return adapter;
	}

	@Override
	public DefaultToColor getAnnotation() {
		return annotation;
	}
}