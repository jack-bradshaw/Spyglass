package com.matthewtamlin.spyglass.annotations.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseShortAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseShort;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestUseShort extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseShort.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseShortAdapter.class;
	}
}