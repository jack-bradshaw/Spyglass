package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorStateListResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;

import java.lang.annotation.Annotation;

public class TestDefaultToColorStateListResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToColorStateListResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToColorStateListResourceAdapter.class;
	}
}