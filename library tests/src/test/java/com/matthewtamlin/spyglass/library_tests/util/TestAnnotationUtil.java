package com.matthewtamlin.spyglass.library_tests.util;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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


	private Field getFieldWithTag(final int tagValue) {
		for (final Field f : TestClass.class.getDeclaredFields()) {
			final FieldTag tag = f.getAnnotation(FieldTag.class);

			if (tag != null && tag.value() == tagValue) {
				return f;
			}
		}

		throw new RuntimeException("No field found with tag index " + tagValue);
	}

	private Method getMethodWithTag(final int tagValue) {
		for (final Method m : TestClass.class.getDeclaredMethods()) {
			final MethodTag tag = m.getAnnotation(MethodTag.class);

			if (tag != null && tag.value() == tagValue) {
				return m;
			}
		}

		throw new RuntimeException("No method found with tag index " + tagValue);
	}

	@SuppressWarnings("unused")
	private static class TestClass {
		
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface FieldTag {
		int value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface MethodTag {
		int value();
	}
}