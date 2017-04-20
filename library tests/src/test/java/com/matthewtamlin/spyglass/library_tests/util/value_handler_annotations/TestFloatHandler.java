package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.FloatHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FloatHandler;

import java.lang.annotation.Annotation;

public class TestFloatHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return FloatHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return FloatHandlerAdapter.class;
	}
}