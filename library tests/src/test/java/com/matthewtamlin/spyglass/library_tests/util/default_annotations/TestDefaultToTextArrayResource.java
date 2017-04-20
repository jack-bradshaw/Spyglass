package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToTextArrayResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextArrayResource;

import java.lang.annotation.Annotation;

public class TestDefaultToTextArrayResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToTextArrayResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToTextArrayResourceAdapter.class;
	}
}