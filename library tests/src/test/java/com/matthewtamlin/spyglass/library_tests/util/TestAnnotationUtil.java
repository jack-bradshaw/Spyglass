package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.library_tests.util.MethodHelper.MethodTag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getCallHandlerAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getDefaultAnnotation;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getUseAnnotations;
import static com.matthewtamlin.spyglass.library.util.AnnotationUtil.getValueHandlerAnnotation;
import static com.matthewtamlin.spyglass.library_tests.util.MethodHelper.getMethodWithTag;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(JUnit4.class)
public class TestAnnotationUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetValueHandlerAnnotation_nullMethod() {
		getValueHandlerAnnotation(null);
	}

	@Test
	public void testGetValueHandlerAnnotation_noAnnotation() {
		final Annotation annotation = getValueHandlerAnnotation(getMethodWithTag(1,
				TestClass.class));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetValueHandlerAnnotation_annotationPresent() {
		final Annotation annotation = getValueHandlerAnnotation(getMethodWithTag(2,
				TestClass.class));

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation, instanceOf(StringHandler.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAnnotation_nullMethod() {
		getDefaultAnnotation(null);
	}

	@Test
	public void testGetDefaultAnnotation_noAnnotation() {
		final Annotation annotation = getDefaultAnnotation(getMethodWithTag(3,
				TestClass.class));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetDefaultAnnotation_annotationPresent() {
		final Annotation annotation = getDefaultAnnotation(getMethodWithTag(4,
				TestClass.class));

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation, instanceOf(DefaultToBoolean.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAnnotations_nullMethod() {
		getUseAnnotations(null);
	}

	@Test
	public void testGetUseAnnotations_noArgs() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(5,
				TestClass.class));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAnnotations_oneArg_noAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(6,
				TestClass.class));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAnnotations_oneArg_oneAnnotation() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(7,
				TestClass.class));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.size(), is(1));

		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(notNullValue()));
		assertThat(annotations.get(0), instanceOf(UseDouble.class));
	}

	@Test
	public void testGetUseAnnotations_threeArgs_noAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(8,
				TestClass.class));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.isEmpty(), is(true));
	}


	@Test
	public void testGetUseAnnotations_threeArgs_twoAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(9,
				TestClass.class));

		assertThat(annotations, is(notNullValue()));
		assertThat(annotations.size(), is(2));

		assertThat(annotations.keySet().contains(0), is(true));
		assertThat(annotations.get(0), is(notNullValue()));
		assertThat(annotations.get(0), instanceOf(UseChar.class));

		assertThat(annotations.keySet().contains(1), is(false));

		assertThat(annotations.keySet().contains(2), is(true));
		assertThat(annotations.get(2), is(notNullValue()));
		assertThat(annotations.get(2), instanceOf(UseLong.class));
	}

	@Test
	public void testGetUseAnnotations_threeArgs_threeAnnotations() {
		final Map<Integer, Annotation> annotations = getUseAnnotations(getMethodWithTag(10,
				TestClass.class));

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

	@Test
	public void testGetCallHandlerAnnotation_noAnnotation() {
		final Annotation annotation = getCallHandlerAnnotation(getMethodWithTag(11,
				TestClass.class));

		assertThat(annotation, is(nullValue()));
	}

	@Test
	public void testGetCallHandlerAnnotation_annotationPresent() {
		final Annotation annotation = getCallHandlerAnnotation(getMethodWithTag(12,
				TestClass.class));

		assertThat(annotation, is(notNullValue()));
		assertThat(annotation, instanceOf(SpecificEnumHandler.class));
	}

	@SuppressWarnings("unused")
	private static class TestClass {
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
		private void method9(@UseChar('a') char c, int i, @UseLong(1L) long l) {}

		@MethodTag(10)
		private void method10(@UseLong(1L) long l, @UseString("s") String s, @UseByte(9) byte b) {}

		@MethodTag(11)
		private void method11() {}

		@MethodTag(12)
		@SpecificEnumHandler(attributeId = 12, ordinal = 1)
		private void method12() {}
	}
}