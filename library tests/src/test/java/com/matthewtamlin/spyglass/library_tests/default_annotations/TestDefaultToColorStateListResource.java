package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import android.support.test.runner.AndroidJUnit4;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultToColorStateListResourceAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;

import org.junit.runner.RunWith;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestDefaultToColorStateListResource extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return DefaultToColorStateListResource.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return DefaultToColorStateListResourceAdapter.class;
	}
}