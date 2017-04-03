package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.DrawableHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.HandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.library.util.AdapterUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class TestAdapterUtil {
	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAdapter_fieldVariant_nullField() {
		AdapterUtil.getHandlerAdapter((Field) null);
	}

	@Test
	public void testGetHandlerAdapter_fieldVariant_noHandlerAnnotations() {
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getFieldWithTag(1));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetHandlerAdapter_fieldVariant_oneHandlerAnnotation() {
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getFieldWithTag(2));

		assertThat(adapter, is(not(nullValue())));
		assertThat(adapter, instanceOf(BooleanHandlerAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAdapter_methodVariant_nullMethod() {
		AdapterUtil.getHandlerAdapter((Method) null);
	}

	@Test
	public void testGetHandlerAdapter_methodVariant_noHandlerAnnotations() {
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getMethodWithTag(1));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetHandlerAdapter_methodVariant_oneHandlerAnnotation() {
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getMethodWithTag(2));

		assertThat(adapter, is(not(nullValue())));
		assertThat(adapter, instanceOf(DrawableHandlerAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_fieldVariant_nullField() {
		AdapterUtil.getDefaultAdapter((Field) null);
	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_noDefaultAnnotations() {

	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_oneDefaultAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_methodVariant_nullMethod() {
		AdapterUtil.getDefaultAdapter((Method) null);
	}

	@Test
	public void testGetDefaultAdapter_methodVariant_noDefaultAnnotations() {

	}

	@Test
	public void testGetHandlerAdapter_methodVariant_oneDefaultAnnotation() {

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAdapters_nullMethod() {
		AdapterUtil.getUseAdapters(null);
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

	private static class TestClass {
		@FieldTag(1)
		private Field field1;

		@FieldTag(2)
		@BooleanHandler(attributeId = 1)
		private Object field2;

		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@DrawableHandler(attributeId = 10)
		private Object method2() {return null;}
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