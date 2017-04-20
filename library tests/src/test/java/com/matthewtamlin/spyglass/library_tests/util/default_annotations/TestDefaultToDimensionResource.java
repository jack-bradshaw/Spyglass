package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;

import java.lang.annotation.Annotation;

public class TestDefaultToDimensionResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToDimensionResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToDimensionResourceAdapter.class;
	}
}