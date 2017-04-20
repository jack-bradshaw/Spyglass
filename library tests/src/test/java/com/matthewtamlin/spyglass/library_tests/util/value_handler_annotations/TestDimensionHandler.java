package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.DimensionHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.DimensionHandler;

import java.lang.annotation.Annotation;

public class TestDimensionHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DimensionHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DimensionHandlerAdapter.class;
	}
}