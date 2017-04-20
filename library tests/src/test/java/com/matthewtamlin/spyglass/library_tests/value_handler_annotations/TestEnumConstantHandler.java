package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.EnumConstantHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.EnumConstantHandler;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestEnumConstantHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return EnumConstantHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return EnumConstantHandlerAdapter.class;
	}
}