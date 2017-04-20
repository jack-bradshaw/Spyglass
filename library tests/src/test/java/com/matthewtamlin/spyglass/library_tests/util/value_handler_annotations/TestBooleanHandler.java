package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.BooleanHandler;

import java.lang.annotation.Annotation;

public class TestBooleanHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return BooleanHandler.class;
	}

	@Override
	public Class<? extends ValueHandlerAdapter> getExpectedAdapterClass() {
		return BooleanHandlerAdapter.class;
	}
}