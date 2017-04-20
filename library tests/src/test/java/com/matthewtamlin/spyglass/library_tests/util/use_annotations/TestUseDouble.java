package com.matthewtamlin.spyglass.library_tests.util.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseDoubleAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;

import java.lang.annotation.Annotation;

public class TestUseDouble extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseDouble.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseDoubleAdapter.class;
	}
}