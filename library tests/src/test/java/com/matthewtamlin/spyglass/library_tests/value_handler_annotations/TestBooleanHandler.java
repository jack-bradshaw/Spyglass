package com.matthewtamlin.spyglass.library_tests.util.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.BooleanHandler;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestBooleanHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return BooleanHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return BooleanHandlerAdapter.class;
	}
}