package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToStringResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;

import java.lang.annotation.Annotation;

public class TestDefaultToStringResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToStringResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToStringResourceAdapter.class;
	}
}