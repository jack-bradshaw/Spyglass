package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToFloatAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFloat;

import org.junit.runner.RunWith;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToFloat extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToFloat.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToFloatAdapter.class;
	}
}