package com.matthewtamlin.spyglass.library_tests.use_adapters;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseDoubleAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;

import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUseDoubleAdapter extends TestUseAdapter<Double, UseDouble> {
	private Double expectedValue;

	private UseDouble annotation;

	private UseAdapter<Double, UseDouble> adapter;

	@Before
	public void setup() {
		expectedValue = Double.POSITIVE_INFINITY;

		annotation = mock(UseDouble.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseDoubleAdapter();
	}

	@Override
	public Double getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseDouble getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Double, UseDouble> getAdapter() {
		return adapter;
	}
}