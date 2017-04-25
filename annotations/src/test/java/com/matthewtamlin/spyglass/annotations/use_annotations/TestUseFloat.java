package com.matthewtamlin.spyglass.annotations.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseFloatAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestUseFloat extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseFloat.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseFloatAdapter.class;
	}
}