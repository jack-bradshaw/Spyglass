package com.matthewtamlin.spyglass.library_tests.use_adapters;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseNullAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseNull;

import org.junit.Before;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class TestUseNullAdapter extends TestUseAdapter<Void, UseNull> {
	private Void expectedValue;

	private UseNull annotation;

	private UseAdapter<Void, UseNull> adapter;

	@Before
	public void setup() {
		expectedValue = null;
		annotation = mock(UseNull.class);
		adapter = new UseNullAdapter();
	}

	@Override
	public Void getExpectedValue() {
		return expectedValue;
	}

	@Override
	public UseNull getAnnotation() {
		return annotation;
	}

	@Override
	public UseAdapter<Void, UseNull> getAdapter() {
		return adapter;
	}

	@Override
	public void testGetValue_validAttributeSupplied() {
		final Void returnedValue = adapter.getValue(annotation);

		assertThat(returnedValue, is(nullValue()));
	}
}