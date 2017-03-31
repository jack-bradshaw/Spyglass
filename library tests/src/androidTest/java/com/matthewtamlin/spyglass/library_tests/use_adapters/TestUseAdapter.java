package com.matthewtamlin.spyglass.library_tests.use_adapters;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public abstract class TestUseAdapter<T, A extends Annotation> {
	public abstract T getExpectedValue();

	public abstract A getAnnotation();

	public abstract UseAdapter<T, A> getAdapter();

	@Test
	public void testReflectiveInstantiation() throws Exception {
		getAdapter().getClass().newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetValue_nullAttributeSupplied() {
		getAdapter().getValue(null);
	}

	@Test
	public void testGetValue_validAttributeSupplied() {
		final T returnedValue = getAdapter().getValue(getAnnotation());

		assertThat(returnedValue, is(getExpectedValue()));
	}
}