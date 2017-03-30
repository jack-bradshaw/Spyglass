package com.matthewtamlin.spyglass.library_tests.use_adapters;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseCharAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;

import org.junit.Before;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUseCharAdapter extends TestUseAdapter<Character, UseChar> {
	private Character expectedValue;

	private UseChar annotation;

	private UseAdapter<Character, UseChar> adapter;

	@Before
	public void setup() {
		expectedValue = Character.MAX_VALUE;

		annotation = mock(UseChar.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseCharAdapter();
	}

	@Override
	public Character getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseChar getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Character, UseChar> getAdapter() {
		return adapter;
	}
}