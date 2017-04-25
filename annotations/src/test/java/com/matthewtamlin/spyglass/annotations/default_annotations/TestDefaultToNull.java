package com.matthewtamlin.spyglass.annotations.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToNullAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToNull extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToNull.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToNullAdapter.class;
	}
}