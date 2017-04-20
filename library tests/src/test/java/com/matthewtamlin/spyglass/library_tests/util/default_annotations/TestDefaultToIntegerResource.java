package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToIntegerResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerResource;

import java.lang.annotation.Annotation;

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