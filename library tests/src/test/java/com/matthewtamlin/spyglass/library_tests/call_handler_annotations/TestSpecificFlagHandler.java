package com.matthewtamlin.spyglass.library_tests.call_handler_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificFlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificFlagHandler;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestSpecificFlagHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return SpecificFlagHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return SpecificFlagHandlerAdapter.class;
	}
}