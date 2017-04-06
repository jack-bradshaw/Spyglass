package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.library.use_annotations.UseInt;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getDefaultAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getHandlerAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getUseAnnotations;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
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
		assertThat(annotation, instanceOf(BooleanHandler.class));
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
		assertThat(annotation, instanceOf(StringHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_fieldVariant_nullField() {
		getDefaultAnnotation((Field) null);
	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_noAnnotation() {
		final Annotation annotation = getDefaultAnnotation(getFieldWithTag(1));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_annotationPresent() {
		final Annotation annotation = getDefaultAnnotation(getFieldWithTag(3));

		assertThat(annotation, is(not(nullValue())));
		assertThat(annotation, instanceOf(StringHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_methodVariant_nullMethod() {
		getDefaultAnnotation((Method) null);
	}

	@Test
	public void testGetDefaultAnnotation_methodVariant_noAnnotation() {
		final Annotation annotation = getDefaultAnnotation(getMethodWithTag(1));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_methodVariant_annotationPresent() {
		final Annotation annotation = getDefaultAnnotation(getMethodWithTag(3));

		assertThat(annotation, is(not(nullValue())));
		assertThat(annotation, instanceOf(BooleanHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAnnotations_nullMethod() {
		getUseAnnotations(null);
	}

	@Test
	public void testGetUseAnnotations_noArgs() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(1));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAnnotations_oneArg_noAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(2));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAnnotations_oneArg_oneAnnotation() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(4));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(false));
		
		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(not(nullValue())));
		assertThat(annotations.get(0), instanceOf(UseString.class));
	}

	@Test
	public void testGetUseAnnotations_threeArgs_twoAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(4));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.size(), is(2));

		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(not(nullValue())));
		assertThat(annotations.get(0), instanceOf(UseChar.class));

		assertThat(annotations.keySet().contains(1), is(true));
		assertThat(annotations.get(1), is(not(nullValue())));
		assertThat(annotations.get(1), instanceOf(UseInt.class));
	}

	@Test
	public void testGetUseAnnotations_threeArgs_threeAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(4));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.size(), is(3));

		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(not(nullValue())));
		assertThat(annotations.get(0), instanceOf(UseBoolean.class));

		assertThat(annotations.keySet().contains(1), is(true));
		assertThat(annotations.get(1), is(not(nullValue())));
		assertThat(annotations.get(1), instanceOf(UseByte.class));

		assertThat(annotations.keySet().contains(2), is(true));
		assertThat(annotations.get(2), is(not(nullValue())));
		assertThat(annotations.get(2), instanceOf(UseDouble.class));
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

		@FieldTag(3)
		@StringHandler(attributeId = 3)
		@DefaultToString("something")
		private String field3;

		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@StringHandler(attributeId = 2)
		private void method2(final String value) {}

		@MethodTag(3)
		@BooleanHandler(attributeId = 3)
		@DefaultToBoolean(false)
		private void method3(final boolean value) {}

		@MethodTag(4)
		private void method4(@UseString("something") final String s) {}

		@MethodTag(5)
		private void method5(@UseChar('a') char c, @UseInt(1) int i, String s) {}

		@MethodTag(6)
		private void method6(
				@UseBoolean(true) boolean b1,
				@UseByte(1) byte b2,
				@UseDouble(2.0) double d) {}
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