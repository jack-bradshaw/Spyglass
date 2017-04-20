package com.matthewtamlin.spyglass.library_tests.default_annotations;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToIntegerResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerResource;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToIntegerResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToIntegerResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToIntegerResourceAdapter.class;
	}
}