package com.matthewtamlin.spyglass.library_tests.util.default_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public abstract class BaseTest {
	public abstract Class<? extends Annotation> getAnnotationUnderTest();

	public abstract Class getExpectedAdapterClass();

	private Default metaAnnotation;

	@Before
	public void setup() {
		// Will be null if not present
		metaAnnotation = getAnnotationUnderTest().getAnnotation(Default.class);
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