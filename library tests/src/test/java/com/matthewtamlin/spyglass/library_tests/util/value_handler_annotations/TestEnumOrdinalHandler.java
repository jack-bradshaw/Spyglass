package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.EnumConstantHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.EnumConstantHandler;

import java.lang.annotation.Annotation;

public class TestEnumOrdinalHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return EnumConstantHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return EnumConstantHandlerAdapter.class;
	}
}