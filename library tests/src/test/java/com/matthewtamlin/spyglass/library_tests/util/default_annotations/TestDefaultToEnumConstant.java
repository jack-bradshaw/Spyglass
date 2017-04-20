package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToEnumConstantAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;

import java.lang.annotation.Annotation;

public class TestDefaultToEnumConstant extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToEnumConstant.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToEnumConstantAdapter.class;
	}
}