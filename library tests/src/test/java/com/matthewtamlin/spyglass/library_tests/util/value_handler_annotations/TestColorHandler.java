package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.ColorHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.ColorHandler;

import java.lang.annotation.Annotation;

public class TestColorHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return ColorHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return ColorHandlerAdapter.class;
	}
}