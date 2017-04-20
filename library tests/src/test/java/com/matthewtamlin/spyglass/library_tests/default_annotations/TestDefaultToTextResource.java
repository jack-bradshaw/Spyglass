package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToTextResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextResource;

import java.lang.annotation.Annotation;

public class TestDefaultToTextResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToTextResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToTextResourceAdapter.class;
	}
}