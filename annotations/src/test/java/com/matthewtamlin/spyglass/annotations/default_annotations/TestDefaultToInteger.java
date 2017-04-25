package com.matthewtamlin.spyglass.annotations.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToIntegerAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToInteger extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToInteger.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToIntegerAdapter.class;
	}
}