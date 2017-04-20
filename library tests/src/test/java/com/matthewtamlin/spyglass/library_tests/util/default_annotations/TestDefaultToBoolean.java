package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

import java.lang.annotation.Annotation;

public class TestDefaultToBoolean extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToBoolean.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToBooleanAdapter.class;
	}
}