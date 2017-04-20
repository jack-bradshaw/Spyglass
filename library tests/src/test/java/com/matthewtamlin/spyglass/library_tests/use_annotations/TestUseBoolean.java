package com.matthewtamlin.spyglass.library_tests.util.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseBooleanAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;

import java.lang.annotation.Annotation;

public class TestUseBoolean extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseBoolean.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseBooleanAdapter.class;
	}
}