package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToIntegerAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;

import java.lang.annotation.Annotation;

public class TestDefaultToInteger extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToInteger.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToIntegerAdapter.class;
	}
}