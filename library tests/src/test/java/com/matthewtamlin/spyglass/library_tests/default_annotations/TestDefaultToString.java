package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToStringAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;

import java.lang.annotation.Annotation;

public class TestDefaultToString extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToString.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToStringAdapter.class;
	}
}