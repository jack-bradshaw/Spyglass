package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFractionResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;

import java.lang.annotation.Annotation;

public class TestDefaultToFractionResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToFractionResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToFractionResourceAdapter.class;
	}
}