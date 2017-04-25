package com.matthewtamlin.spyglass.annotations.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.FloatHandlerAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestFloatHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return FloatHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return FloatHandlerAdapter.class;
	}
}