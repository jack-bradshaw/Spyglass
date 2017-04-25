package com.matthewtamlin.spyglass.annotations.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseNullAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseNull;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestUseNull extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseNull.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseNullAdapter.class;
	}
}