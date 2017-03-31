package com.matthewtamlin.spyglass.library_tests.use_adapters;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseLongAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class TestUseLongAdapter extends TestUseAdapter<Long, UseLong> {
	private Long expectedValue;

	private UseLong annotation;

	private UseAdapter<Long, UseLong> adapter;

	@Before
	public void setup() {
		expectedValue = Long.MAX_VALUE;

		annotation = mock(UseLong.class);
		when(annotation.value()).thenReturn(expectedValue);

		adapter = new UseLongAdapter();
	}

	@Override
	public Long getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseLong getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Long, UseLong> getAdapter() {
		return adapter;
	}
}