package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

import org.junit.Before;
import org.junit.runner.RunWith;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToDimensionAdapter extends TestDefaultAdapter<
		Float,
		DefaultToDimension,
		DefaultToDimensionAdapter> {

	private static final float DEFAULT_VALUE_DP = 100;

	private Context context;

	private Float expectedDefaultValue;

	private DefaultToDimensionAdapter adapter;

	private DefaultToDimension annotation;

	@Before
	public void setup() {
		context = InstrumentationRegistry.getTargetContext();

		expectedDefaultValue = DP.convertToPx(context, DEFAULT_VALUE_DP);

		adapter = new DefaultToDimensionAdapter();

		annotation = mock(DefaultToDimension.class);
		when(annotation.value()).thenReturn(DEFAULT_VALUE_DP);
		when(annotation.unit()).thenReturn(DP);
	}

	@Override
	public Float getExpectedDefaultValue() {
		return expectedDefaultValue;
	}

	@Override
	public DefaultToDimension getAnnotation() {
		return annotation;
	}

	@Override
	public DefaultToDimensionAdapter getAdapter() {
		return adapter;
	}

	@Override
	public Context getContext() {
		return context;
	}
}