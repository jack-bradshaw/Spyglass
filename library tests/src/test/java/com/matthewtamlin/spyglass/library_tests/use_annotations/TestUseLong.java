package com.matthewtamlin.spyglass.library_tests.util.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseLongAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestUseLong extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseLong.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseLongAdapter.class;
	}
}