package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToSuppliedValueAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToSuppliedValue;

import java.lang.annotation.Annotation;

public class TestDefaultToSuppliedValue extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToSuppliedValue.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToSuppliedValueAdapter.class;
	}
}