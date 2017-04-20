package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.StringHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import java.lang.annotation.Annotation;

public class TestStringHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return StringHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return StringHandlerAdapter.class;
	}
}