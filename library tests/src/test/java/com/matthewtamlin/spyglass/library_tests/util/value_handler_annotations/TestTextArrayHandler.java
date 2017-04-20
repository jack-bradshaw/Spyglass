package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.TextArrayHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.TextArrayHandler;

import java.lang.annotation.Annotation;

public class TestTextArrayHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return TextArrayHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return TextArrayHandlerAdapter.class;
	}
}