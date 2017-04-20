package com.matthewtamlin.spyglass.library_tests.util.call_handler_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.FlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_annotations.FlagHandler;

import java.lang.annotation.Annotation;

public class TestFlagHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return FlagHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return FlagHandlerAdapter.class;
	}
}