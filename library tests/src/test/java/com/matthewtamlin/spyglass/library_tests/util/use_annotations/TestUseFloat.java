package com.matthewtamlin.spyglass.library_tests.util.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseFloatAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;

import java.lang.annotation.Annotation;

public class TestUseFloat extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseFloat.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseFloatAdapter.class;
	}
}