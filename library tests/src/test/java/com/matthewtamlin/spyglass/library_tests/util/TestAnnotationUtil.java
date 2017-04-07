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
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;
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
		final Annotation annotation = getHandlerAnnotation(getFieldWithTag(2));

		assertThat(annotation, is(notNullValue()));
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

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation, instanceOf(StringHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_fieldVariant_nullField() {
		getDefaultAnnotation((Field) null);
	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_noAnnotation() {
		final Annotation annotation = getDefaultAnnotation(getFieldWithTag(3));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_fieldVariant_annotationPresent() {
		final Annotation annotation = getDefaultAnnotation(getFieldWithTag(4));

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation, instanceOf(DefaultToString.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_methodVariant_nullMethod() {
		getDefaultAnnotation((Method) null);
	}

	@Test
	public void testGetDefaultAnnotation_methodVariant_noAnnotation() {
		final Annotation annotation = getDefaultAnnotation(getMethodWithTag(3));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_methodVariant_annotationPresent() {
		final Annotation annotation = getDefaultAnnotation(getMethodWithTag(4));

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation, instanceOf(BooleanHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAnnotations_nullMethod() {
		getUseAnnotations(null);
	}

	@Test
	public void testGetUseAnnotations_noArgs() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(5));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAnnotations_oneArg_noAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(6));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAnnotations_oneArg_oneAnnotation() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(7));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.size(), is(1));

		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(notNullValue()));
		assertThat(annotations.get(0), instanceOf(UseDouble.class));
	}

	@Test
	public void testGetUseAnnotations_threeArgs_noAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(8));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}


	@Test
	public void testGetUseAnnotations_threeArgs_twoAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(9));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.size(), is(2));

		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(notNullValue()));
		assertThat(annotations.get(0), instanceOf(UseChar.class));

		assertThat(annotations.keySet().contains(1), is(true));
		assertThat(annotations.get(1), is(notNullValue()));
		assertThat(annotations.get(1), instanceOf(UseInt.class));
	}

	@Test
	public void testGetUseAnnotations_threeArgs_threeAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(10));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.size(), is(3));

		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(notNullValue()));
		assertThat(annotations.get(0), instanceOf(UseLong.class));

		assertThat(annotations.keySet().contains(1), is(true));
		assertThat(annotations.get(1), is(notNullValue()));
		assertThat(annotations.get(1), instanceOf(UseString.class));

		assertThat(annotations.keySet().contains(2), is(true));
		assertThat(annotations.get(2), is(notNullValue()));
		assertThat(annotations.get(2), instanceOf(UseByte.class));
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
		private Object field3;

		@FieldTag(4)
		@DefaultToString("something")
		private String field4;

		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@StringHandler(attributeId = 2)
		private void method2(String value) {}

		@MethodTag(3)
		private Object method3() {return null;}

		@MethodTag(4)
		@DefaultToBoolean(true)
		private void method4(boolean b) {}

		@MethodTag(5)
		private void method5() {}

		@MethodTag(6)
		private void method6(boolean b) {}

		@MethodTag(7)
		private void method7(@UseDouble(2.0) double d) {}

		@MethodTag(8)
		private void method8(float f, boolean b, char c) {}

		@MethodTag(9)
		private void method9(@UseChar('a') char c, @UseInt(1) int i, long l) {}

		@MethodTag(10)
		private void method10(@UseLong(1L) long l, @UseString("s") String s, @UseByte(9) byte b) {}
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