package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.ColorStateListHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.ColorStateListHandler;

import java.lang.annotation.Annotation;

public class TestColorStateListHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return ColorStateListHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return ColorStateListHandlerAdapter.class;
	}
}