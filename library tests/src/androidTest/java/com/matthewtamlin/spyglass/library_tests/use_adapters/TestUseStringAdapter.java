package com.matthewtamlin.spyglass.library_tests.use_adapters;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseStringAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestUseStringAdapter extends TestUseAdapter<String, UseString> {
	private String expectedValue;

	private UseString annotation;

	private UseAdapter<String, UseString> adapter;

	@Before
	public void setup() {
		expectedValue = "This is a String...";

		annotation = mock(UseString.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseStringAdapter();
	}

	@Override
	public String getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseString getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<String, UseString> getAdapter() {
		return adapter;
	}
}