package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.handler_adapters.BooleanHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.DrawableHandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_adapters.HandlerAdapter;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;
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
import java.util.Map;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getUseAdapters;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
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

		assertThat(adapter, is(notNullValue()));
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

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(DrawableHandlerAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_fieldVariant_nullField() {
		AdapterUtil.getDefaultAdapter((Field) null);
	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_noDefaultAnnotations() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getFieldWithTag(1));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_oneDefaultAnnotation() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getFieldWithTag(3));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(DefaultToString.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_methodVariant_nullMethod() {
		AdapterUtil.getDefaultAdapter((Method) null);
	}

	@Test
	public void testGetDefaultAdapter_methodVariant_noDefaultAnnotations() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getMethodWithTag(1));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetHandlerAdapter_methodVariant_oneDefaultAnnotation() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getMethodWithTag(3));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(DefaultToDimensionAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAdapters_nullMethod() {
		getUseAdapters(null);
	}

	@Test
	public void testGetUseAdapters_noArguments() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(4));

		assertThat(adapters.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAdapters_oneArgument_noUseAnnotations() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(5));

		assertThat(adapters.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAdapters_oneArgument_oneUseAnnotation() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(6));

		assertThat(adapters.size(), is(1));
		assertThat(adapters.get(0), instanceOf(UseBoolean.class));
	}

	@Test
	public void testGetUseAdapters_threeArguments_twoUseAnnotations() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(7));

		assertThat(adapters.size(), is(2));
		assertThat(adapters.get(0), instanceOf(UseBoolean.class));
		assertThat(adapters.get(1), instanceOf(UseChar.class));
	}

	@Test
	public void testGetUseAdapters_threeArguments_threeUseAnnotations() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(8));

		assertThat(adapters.size(), is(3));
		assertThat(adapters.get(0), instanceOf(UseBoolean.class));
		assertThat(adapters.get(1), instanceOf(UseChar.class));
		assertThat(adapters.get(2), instanceOf(UseString.class));
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
		@FieldTag(1)
		private Field field1;

		@FieldTag(2)
		@BooleanHandler(attributeId = 2)
		private Object field2;

		@FieldTag(3)
		@BooleanHandler(attributeId = 3)
		@DefaultToString("default string")
		private Object field3;

		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@DrawableHandler(attributeId = 2)
		private Object method2(int i) {return null;}

		@MethodTag(3)
		@DrawableHandler(attributeId = 3)
		@DefaultToDimension(value = 10, unit = DP)
		private Object method3(int i) {return null;}

		@MethodTag(4)
		@StringHandler(attributeId = 4)
		private void method4() {}

		@MethodTag(5)
		@FractionHandler(attributeId = 5)
		private void method5(final int i) {}

		@MethodTag(6)
		@ColorHandler(attributeId = 6)
		private void method6(@UseBoolean(false) final int i) {}

		@MethodTag(7)
		@ColorHandler(attributeId = 7)
		private void method7(
				@UseBoolean(false) final int i,
				@UseChar(0) final char c,
				final String s) {}

		@MethodTag(8)
		@ColorHandler(attributeId = 8)
		private void method8(
				@UseBoolean(false) final int i,
				@UseChar(0) final char c,
				@UseString("string") final String s) {}
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