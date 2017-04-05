package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.library.util.AnnotationUtil;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getDefaultAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getHandlerAnnotation;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.AdditionalMatchers.not;

public class TestAnnotationUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAnnotation_fieldVariant_nullField() {
		getHandlerAnnotation((Field) null);
	}

	@Test
	public void testGetHandlerAnnotation_fieldVariant_noAnnotation() {
		final Annotation annotation = getHandlerAnnotation(getFieldWithTag(1));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetHandlerAnnotation_fieldVariant_annotationPresent() {
		final Annotation annotation = getHandlerAnnotation(getFieldWithTag(1));

		assertThat(annotation, is(not(nullValue())));
		assertThat(annotation.getClass(), instanceOf(BooleanHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAnnotation_methodVariant_nullMethod() {
		getHandlerAnnotation((Method) null);
	}

	@Test
	public void testGetHandlerAnnotation_methodVariant_noAnnotation() {
		final Annotation annotation = getHandlerAnnotation(getMethodWithTag(1));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetHandlerAnnotation_methodVariant_annotationPresent() {
		final Annotation annotation = getHandlerAnnotation(getMethodWithTag(2));

		assertThat(annotation, is(not(nullValue())));
		assertThat(annotation.getClass(), instanceOf(StringHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_fieldVariant_nullField() {
		getDefaultAnnotation((Field) null);
	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_noAnnotation() {

	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_annotationPresent() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_methodVariant_nullMethod() {
		getDefaultAnnotation((Method) null);
	}

	@Test
	public void testGetDefaultAnnotation_methodVariant_noAnnotation() {
		final Annotation annotation = getDefaultAnnotation(getFieldWithTag(1));

		assertThat(annotation, is(nullValue()));
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


	private static Field getFieldWithTag(final int tagValue) {
		for (final Field f : TestClass.class.getDeclaredFields()) {
			final FieldTag tag = f.getAnnotation(FieldTag.class);

			if (tag != null && tag.value() == tagValue) {
				return f;
			}
		}

		throw new RuntimeException("No field found with tag index " + tagValue);
	}

	private static Method getMethodWithTag(final int tagValue) {
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
		@FieldTag(1)
		private Object field1;

		@FieldTag(2)
		@BooleanHandler(attributeId = 2)
		private Boolean field2;

		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@StringHandler(attributeId = 2)
		private void method2(final String value) {}
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface FieldTag {
		int value();
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface MethodTag {
		int value();
	}
}