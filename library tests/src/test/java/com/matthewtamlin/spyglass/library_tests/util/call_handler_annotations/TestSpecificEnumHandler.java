package com.matthewtamlin.spyglass.library_tests.util.call_handler_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificEnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificEnumHandler;

import java.lang.annotation.Annotation;

public class TestSpecificEnumHandler extends TestCallHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return SpecificEnumHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return SpecificEnumHandlerAdapter.class;
	}
}