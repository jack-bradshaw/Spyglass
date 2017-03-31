package com.matthewtamlin.spyglass.library_tests.use_adapters;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseIntAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseInt;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestUseIntAdapter extends TestUseAdapter<Integer, UseInt> {
	private Integer expectedValue;

	private UseInt annotation;

	private UseAdapter<Integer, UseInt> adapter;

	@Before
	public void setup() {
		expectedValue = Integer.MAX_VALUE;

		annotation = mock(UseInt.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseIntAdapter();
	}

	@Override
	public Integer getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseInt getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Integer, UseInt> getAdapter() {
		return adapter;
	}
}