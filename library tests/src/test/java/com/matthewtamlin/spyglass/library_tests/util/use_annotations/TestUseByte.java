package com.matthewtamlin.spyglass.library_tests.util.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseByteAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;

import java.lang.annotation.Annotation;

public class TestUseByte extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseByte.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseByteAdapter.class;
	}
}