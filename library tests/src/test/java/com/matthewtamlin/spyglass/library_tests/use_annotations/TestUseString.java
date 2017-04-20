package com.matthewtamlin.spyglass.library_tests.util.use_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseStringAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;

import java.lang.annotation.Annotation;

@RunWith(JUnit4.class)
public class TestUseString extends BaseTest {
	@Override
	public Class<? extends Annotation> getAnnotationUnderTest() {
		return UseString.class;
	}

	@Override
	public Class getExpectedAdapterClass() {
		return UseStringAdapter.class;
	}
}