package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;

import java.lang.annotation.Annotation;

public class TestDefaultToBooleanResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToBooleanResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToBooleanResourceAdapter.class;
	}
}