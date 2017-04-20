package com.matthewtamlin.spyglass.library_tests.util.use_annotations;


import com.matthewtamlin.spyglass.library.use_adapters.UseSuppliedValueAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseSuppliedValue;

import java.lang.annotation.Annotation;

public class TestUseSuppliedValue extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseSuppliedValue.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseSuppliedValueAdapter.class;
	}
}