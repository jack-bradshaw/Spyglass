package com.matthewtamlin.spyglass.annotations.value_handler_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.FractionHandlerAdapter;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FractionHandler;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestFractionHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return FractionHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return FractionHandlerAdapter.class;
	}
}