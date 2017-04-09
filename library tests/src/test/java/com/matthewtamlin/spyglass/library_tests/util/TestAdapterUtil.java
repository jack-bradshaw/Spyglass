package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToDimensionAdapter;
import com.matthewtamlin.spyglass.library.default_adapters.DefaultToStringAdapter;
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
import com.matthewtamlin.spyglass.library.use_adapters.UseBooleanAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseCharAdapter;
import com.matthewtamlin.spyglass.library.use_adapters.UseStringAdapter;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;
import com.matthewtamlin.spyglass.library.util.AdapterUtil;
import com.matthewtamlin.spyglass.library_tests.util.FieldHelper.FieldTag;
import com.matthewtamlin.spyglass.library_tests.util.MethodHelper.MethodTag;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;
import static com.matthewtamlin.spyglass.library.util.AdapterUtil.getUseAdapters;
import static com.matthewtamlin.spyglass.library_tests.util.FieldHelper.getFieldWithTag;
import static com.matthewtamlin.spyglass.library_tests.util.MethodHelper.getMethodWithTag;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
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
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getFieldWithTag(1,
				TestClass.class));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetHandlerAdapter_fieldVariant_oneHandlerAnnotation() {
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getFieldWithTag(2,
				TestClass.class));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(BooleanHandlerAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetHandlerAdapter_methodVariant_nullMethod() {
		AdapterUtil.getHandlerAdapter((Method) null);
	}

	@Test
	public void testGetHandlerAdapter_methodVariant_noHandlerAnnotations() {
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getMethodWithTag(1,
				TestClass.class));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetHandlerAdapter_methodVariant_oneHandlerAnnotation() {
		final HandlerAdapter adapter = AdapterUtil.getHandlerAdapter(getMethodWithTag(2,
				TestClass.class));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(DrawableHandlerAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_fieldVariant_nullField() {
		AdapterUtil.getDefaultAdapter((Field) null);
	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_noDefaultAnnotations() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getFieldWithTag(3,
				TestClass.class));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetDefaultAdapter_fieldVariant_oneDefaultAnnotation() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getFieldWithTag(4,
				TestClass.class));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(DefaultToStringAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetDefaultAdapter_methodVariant_nullMethod() {
		AdapterUtil.getDefaultAdapter((Method) null);
	}

	@Test
	public void testGetDefaultAdapter_methodVariant_noDefaultAnnotations() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getMethodWithTag(1,
				TestClass.class));

		assertThat(adapter, is(nullValue()));
	}

	@Test
	public void testGetHandlerAdapter_methodVariant_oneDefaultAnnotation() {
		final DefaultAdapter adapter = AdapterUtil.getDefaultAdapter(getMethodWithTag(3,
				TestClass.class));

		assertThat(adapter, is(notNullValue()));
		assertThat(adapter, instanceOf(DefaultToDimensionAdapter.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetUseAdapters_nullMethod() {
		getUseAdapters(null);
	}

	@Test
	public void testGetUseAdapters_noArguments() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(4,
				TestClass.class));

		assertThat(adapters.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAdapters_oneArgument_noUseAnnotations() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(5,
				TestClass.class));

		assertThat(adapters.isEmpty(), is(true));
	}

	@Test
	public void testGetUseAdapters_oneArgument_oneUseAnnotation() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(6,
				TestClass.class));

		assertThat(adapters.size(), is(1));
		assertThat(adapters.get(0), instanceOf(UseBooleanAdapter.class));
	}

	@Test
	public void testGetUseAdapters_threeArguments_twoUseAnnotations() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(7,
				TestClass.class));

		assertThat(adapters.size(), is(2));
		assertThat(adapters.get(0), instanceOf(UseBooleanAdapter.class));
		assertThat(adapters.get(1), instanceOf(UseCharAdapter.class));
	}

	@Test
	public void testGetUseAdapters_threeArguments_threeUseAnnotations() {
		final Map<Integer, UseAdapter> adapters = AdapterUtil.getUseAdapters(getMethodWithTag(8,
				TestClass.class));

		assertThat(adapters.size(), is(3));
		assertThat(adapters.get(0), instanceOf(UseBooleanAdapter.class));
		assertThat(adapters.get(1), instanceOf(UseCharAdapter.class));
		assertThat(adapters.get(2), instanceOf(UseStringAdapter.class));
	}

	@SuppressWarnings("unused")
	private static class TestClass {
		@FieldTag(1)
		private Field field1;

		@FieldTag(2)
		@BooleanHandler(attributeId = 2)
		private Object field2;

		@FieldTag(3)
		private Object field3;

		@FieldTag(4)
		@BooleanHandler(attributeId = 4)
		@DefaultToString("default string")
		private Object field4;

		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@DrawableHandler(attributeId = 2)
		private Object method2(int i) {return null;}

		@MethodTag(3)
		private void method3() {}

		@MethodTag(4)
		@DrawableHandler(attributeId = 4)
		@DefaultToDimension(value = 10, unit = DP)
		private Object method4(int i) {return null;}

		@MethodTag(5)
		@StringHandler(attributeId = 5)
		private void method5() {}

		@MethodTag(6)
		@FractionHandler(attributeId = 6)
		private void method6(int i) {}

		@MethodTag(7)
		@ColorHandler(attributeId = 7)
		private void method7(@UseBoolean(false) int i) {}

		@MethodTag(8)
		@DrawableHandler(attributeId = 8)
		private void method8(String s, boolean b, float f) {}

		@MethodTag(9)
		@ColorHandler(attributeId = 9)
		private void method8(@UseBoolean(false) int i, @UseChar(0) char c, String s) {}

		@MethodTag(10)
		@ColorHandler(attributeId = 10)
		private void method9(
				@UseBoolean(false) int i,
				@UseChar(0) char c,
				@UseString("s") String s) {}
	}
}