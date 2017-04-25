package com.matthewtamlin.spyglass.annotations.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToDimension extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToDimension.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToDimensionAdapter.class;
	}
}