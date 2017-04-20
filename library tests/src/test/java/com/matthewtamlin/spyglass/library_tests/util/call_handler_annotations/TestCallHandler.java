package com.matthewtamlin.spyglass.library_tests.util.call_handler_annotations;

import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public abstract class TestCallHandler {
	public abstract Class<? extends Annotation> getAnnotationUnderTest();

	public abstract Class getExpectedAdapterClass();

	private CallHandler metaAnnotation;

	@Before
	public void setup() {
		// Will be null if not present
		metaAnnotation = getAnnotationUnderTest().getAnnotation(CallHandler.class);
	}

	@Test
	public void testMetaAnnotationIsPresent() {
		assertThat("Meta-annotation is missing.", metaAnnotation, is(notNullValue()));
	}

	@Test
	public void testMetaAnnotationHasCorrectAdapter() {
		assertThat("Meta-annotation specifies wrong adapter.",
				metaAnnotation.adapterClass(),
				equalTo(getExpectedAdapterClass()));
	}
}