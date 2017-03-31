package com.matthewtamlin.spyglass.library_tests.use_adapters;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseShortAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseShort;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestUseShortAdapter extends TestUseAdapter<Short, UseShort> {
	private Short expectedValue;

	private UseShort annotation;

	private UseAdapter<Short, UseShort> adapter;

	@Before
	public void setup() {
		expectedValue = Short.MAX_VALUE;

		annotation = mock(UseShort.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseShortAdapter();
	}

	@Override
	public Short getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseShort getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Short, UseShort> getAdapter() {
		return adapter;
	}
}