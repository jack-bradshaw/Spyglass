package com.matthewtamlin.spyglass.library_tests.default_annotations;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

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