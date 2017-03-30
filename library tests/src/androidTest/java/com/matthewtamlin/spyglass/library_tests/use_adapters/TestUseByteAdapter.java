package com.matthewtamlin.spyglass.library_tests.use_adapters;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseByteAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;

import org.junit.Before;

import java.lang.annotation.Annotation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUseByteAdapter extends TestUseAdapter {
	private Byte expectedValue;

	private UseByte annotation;

	private UseAdapter<Byte, UseByte> adapter;

	@Before
	public void setup() {
		expectedValue = Byte.MAX_VALUE;

		annotation = mock(UseByte.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseByteAdapter();
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
	public UseAdapter<Byte, UseByte> getAdapter() {
		return adapter;
	}
}