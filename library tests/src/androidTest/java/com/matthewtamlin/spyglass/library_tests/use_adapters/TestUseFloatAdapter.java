package com.matthewtamlin.spyglass.library_tests.use_adapters;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseFloatAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestUseFloatAdapter extends TestUseAdapter<Float, UseFloat> {
	private Float expectedValue;

	private UseFloat annotation;

	private UseAdapter<Float, UseFloat> adapter;

	@Before
	public void setup() {
		expectedValue = Float.POSITIVE_INFINITY;

		annotation = mock(UseFloat.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseFloatAdapter();
	}

	@Override
	public Float getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseFloat getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Float, UseFloat> getAdapter() {
		return adapter;
	}
}