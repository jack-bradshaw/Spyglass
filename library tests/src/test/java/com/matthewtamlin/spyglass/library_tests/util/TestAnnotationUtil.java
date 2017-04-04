package com.matthewtamlin.spyglass.library_tests.util;

import org.junit.Test;

public class TestAnnotationUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAnnotation_fieldVariant_nullField() {
		
	}

	@Test
	public void testGetHandlerAnnotation_fieldVariant_noAnnotation() {

	}

	@Test
	public void testGetHandlerAnnotation_fieldVariant_annotationPresent() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAnnotation_methodVariant_nullMethod() {

	}

	@Test
	public void testGetHandlerAnnotation_methodVariant_noAnnotation() {

	}

	@Test
	public void testGetHandlerAnnotation_methodVariant_annotationPresent() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_fieldVariant_nullField() {

	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_noAnnotation() {

	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_annotationPresent() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_methodVariant_nullMethod() {

	}

	@Test
	public void testGetDefaultAnnotation_methodVariant_noAnnotation() {

	}

	@Test
	public void testGetDefaultAnnotation_methodVariant_annotationPresent() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAnnotations_nullMethod() {

	}

	@Test
	public void testGetUseAnnotations_noArgs() {

	}

	@Test
	public void testGetUseAnnotations_oneArg_noAnnotations() {

	}

	@Test
	public void testGetUseAnnotations_oneArg_oneAnnotation() {

	}

	@Test
	public void testGetUseAnnotations_threeArgs_twoAnnotations() {

	}

	@Test
	public void testGetUseAnnotations_threeArgs_threeAnnotations() {

	}
}