package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFloatAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFloat;

import java.lang.annotation.Annotation;

public class TestDefaultToFloat extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToFloat.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToFloatAdapter.class;
	}
}