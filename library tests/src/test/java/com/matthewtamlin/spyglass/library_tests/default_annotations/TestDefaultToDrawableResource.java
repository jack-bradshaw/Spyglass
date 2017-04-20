package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDrawableResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;

import java.lang.annotation.Annotation;

public class TestDefaultToDrawableResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToDrawableResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToDrawableResourceAdapter.class;
	}
}