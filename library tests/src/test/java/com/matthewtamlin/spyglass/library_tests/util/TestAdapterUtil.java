package com.matthewtamlin.spyglass.library_tests.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestAdapterUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAdapter_fieldVariant_nullField() {

	}

	public void testGetHandlerAdapter_fieldVariant_noHandlerAnnotations() {

	}

	public void testGetHandlerAdapter_fieldVariant_oneHandlerAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAdapter_methodVariant_nullMethod() {

	}

	public void testGetHandlerAdapter_methodVariant_noHandlerAnnotations() {

	}

	public void testGetHandlerAdapter_methodVariant_oneHandlerAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_fieldVariant_nullField() {

	}

	public void testGetDefaultAdapter_fieldVariant_noDefaultAnnotations() {

	}

	public void testGetDefaultAdapter_fieldVariant_oneDefaultAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_methodVariant_nullMethod() {

	}

	public void testGetDefaultAdapter_methodVariant_noDefaultAnnotations() {

	}

	public void testGetHandlerAdapter_methodVariant_oneDefaultAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAdapters_nullMethod() {

	}

	@Test
	public void testGetUseAdapters_noArguments() {

	}

	@Test
	public void testGetUseAdapters_oneArgument_noUseAnnotations() {

	}

	@Test
	public void testGetUseAdapters_oneArgument_oneUseAnnotation() {

	}

	@Test
	public void testGetUseAdapters_threeArguments_twoUseAnnotations() {

	}

	@Test
	public void testGetUseAdapters_threeArguments_threeUseAnnotations() {

	}
}