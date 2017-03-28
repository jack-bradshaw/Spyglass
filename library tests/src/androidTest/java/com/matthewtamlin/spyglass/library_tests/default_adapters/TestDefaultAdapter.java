package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;

import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public abstract class TestDefaultAdapter<
		D,
		A extends Annotation,
		P extends DefaultAdapter<D, A>> {

	public abstract Context getContext();

	public abstract D getExpectedDefaultValue();

	public abstract P getAdapter();

	public abstract A getAnnotation();

	@Test
	public void testReflectiveInstantiation() throws Exception {
		getAdapter().getClass().newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefault_nullAnnotation() {
		getAdapter().getDefault(null, getContext());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefault_nullContext() {
		getAdapter().getDefault(getAnnotation(), null);
	}

	@Test
	public void testGetDefault_validArguments() {
		final D returnedDefault = getAdapter().getDefault(getAnnotation(), getContext());

		assertThat(returnedDefault, is(getExpectedDefaultValue()));
	}
}