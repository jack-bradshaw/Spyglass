package com.matthewtamlin.spyglass.library_tests.use_adapters;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseBooleanAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;

import org.junit.Before;

import java.lang.annotation.Annotation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUseBooleanAdapter extends TestUseAdapter {
	private Boolean expectedValue;

	private UseBoolean annotation;

	private UseAdapter<Boolean, UseBoolean> adapter;

	@Before
	public void setup() {
		expectedValue = Boolean.TRUE;

		annotation = mock(UseBoolean.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseBooleanAdapter();
	}

	@Override
	public Object getExpectedValue() {
		return expectedValue;
	}

	@Override
	public Annotation getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Boolean, UseBoolean> getAdapter() {
		return adapter;
	}
}