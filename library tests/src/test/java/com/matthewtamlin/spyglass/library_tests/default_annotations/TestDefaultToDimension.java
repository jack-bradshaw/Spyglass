package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

import java.lang.annotation.Annotation;

public class TestDefaultToDimension extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToDimension.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToDimensionAdapter.class;
	}
}