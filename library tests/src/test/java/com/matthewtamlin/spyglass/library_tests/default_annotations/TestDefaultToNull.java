package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToNullAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;

import java.lang.annotation.Annotation;

public class TestDefaultToNull extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToNull.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToNullAdapter.class;
	}
}