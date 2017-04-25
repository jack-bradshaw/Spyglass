package com.matthewtamlin.spyglass.annotations.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseCharAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestUseChar extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseChar.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseCharAdapter.class;
	}
}