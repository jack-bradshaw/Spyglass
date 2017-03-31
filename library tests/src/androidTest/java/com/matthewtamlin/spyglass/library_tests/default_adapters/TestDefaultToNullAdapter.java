package com.matthewtamlin.spyglass.library_tests.default_adapters;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToNullAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class TestDefaultToNullAdapter {
	private DefaultToNullAdapter adapter;

	private DefaultToNull annotation;

	@Before
	public void setup() {
		adapter = new DefaultToNullAdapter();
		annotation = mock(DefaultToNull.class);
	}

	@Test
	public void testReflectiveInstantiation() throws Exception {
		adapter.getClass().newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefault_nullAnnotation() {
		adapter.getDefault(null, mock(Context.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefault_nullContext() {
		adapter.getDefault(annotation, null);
	}

	@Test
	public void testGetDefault_validArguments() {
		final Object returnedDefault = adapter.getDefault(annotation, mock(Context.class));

		assertThat(returnedDefault, is((nullValue())));
	}
}