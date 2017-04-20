package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.TextHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.TextHandler;

import java.lang.annotation.Annotation;

public class TestTextHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return TextHandler.class;
	}

	@Override
	public Class<? extends ValueHandlerAdapter> getExpectedAdapterClass() {
		return TextHandlerAdapter.class;
	}
}