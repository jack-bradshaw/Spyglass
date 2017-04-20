package com.matthewtamlin.spyglass.library_tests.util.call_handler_annotations;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificEnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificEnumHandler;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestSpecificEnumHandler extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return SpecificEnumHandler.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return SpecificEnumHandlerAdapter.class;
	}
}