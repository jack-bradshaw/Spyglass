package com.matthewtamlin.spyglass.library_tests.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToBooleanAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToBoolean extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToBoolean.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToBooleanAdapter.class;
	}
}