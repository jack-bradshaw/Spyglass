package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.DrawableHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.DrawableHandler;

import java.lang.annotation.Annotation;

public class TestDrawableHandler extends TestValueHandler {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DrawableHandler.class;
	}

	@Override
	public Class<? extends ValueHandlerAdapter> getExpectedAdapterClass() {
		return DrawableHandlerAdapter.class;
	}
}