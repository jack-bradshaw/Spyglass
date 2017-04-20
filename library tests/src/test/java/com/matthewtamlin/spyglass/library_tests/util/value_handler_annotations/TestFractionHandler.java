package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.FractionHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FractionHandler;

import java.lang.annotation.Annotation;

public class TestFractionHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return FractionHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return FractionHandlerAdapter.class;
	}
}