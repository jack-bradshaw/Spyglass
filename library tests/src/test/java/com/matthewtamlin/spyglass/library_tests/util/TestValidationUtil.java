package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.call_handler_annotations.FlagHandler;
import com.matthewtamlin.spyglass.library.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;
import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.library.use_annotations.UseInt;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;
import com.matthewtamlin.spyglass.library.use_annotations.UseNull;
import com.matthewtamlin.spyglass.library.use_annotations.UseString;
import com.matthewtamlin.spyglass.library.util.SpyglassValidationException;
import com.matthewtamlin.spyglass.library.util.ValidationUtil;
import com.matthewtamlin.spyglass.library.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class TestValidationUtil {
	private static final String FIELD_MESSAGE = "Test for field %1$s failed with reason \"%2$s\"";

	private static final String METHOD_MESSAGE = "Test for method %1$s failed with reason \"%2$s\"";

	@Test
	public void testValidateField_usingFieldsOfTestClass() {
		for (final Field f : TestClass.class.getDeclaredFields()) {
			if (!f.isAnnotationPresent(ValidationTestTarget.class)) {
				continue;
			}

			final ValidationTestTarget annotation = f.getAnnotation(ValidationTestTarget.class);

			final boolean shouldPass = annotation.isValid();
			final boolean doesPass = passesValidation(f);

			final String message = String.format(
					FIELD_MESSAGE,
					f.getName(),
					annotation.failureMessage());

			assertThat(message, shouldPass, is(doesPass));
		}
	}

	@Test
	public void testValidateMethod_usingMethodsOfTestClass() {
		for (final Method m : TestClass.class.getDeclaredMethods()) {
			if (!m.isAnnotationPresent(ValidationTestTarget.class)) {
				continue;
			}

			final ValidationTestTarget annotation = m.getAnnotation(ValidationTestTarget.class);

			final boolean shouldPass = annotation.isValid();
			final boolean doesPass = passesValidation(m);

			final String message = String.format(
					METHOD_MESSAGE,
					m.getName(),
					annotation.failureMessage());

			assertThat(message, shouldPass, is(doesPass));
		}
	}

	public boolean passesValidation(final Field field) {
		try {
			ValidationUtil.validateField(field);
			return true;
		} catch (final SpyglassValidationException e) {
			return false;
		}
	}

	public boolean passesValidation(final Method method) {
		try {
			ValidationUtil.validateMethod(method);
			return true;
		} catch (final SpyglassValidationException e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	public class TestClass {
		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Fields with no annotations should pass.")
		private Object field1;

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Fields with just one handler should pass.")
		@BooleanHandler(attributeId = 1)
		private Object field2;

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Fields with multiple handlers should fail.")
		@BooleanHandler(attributeId = 1)
		@StringHandler(attributeId = 1)
		private Object field3;

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Fields with multiple handlers should fail.")
		@IntegerHandler(attributeId = 1)
		@DimensionHandler(attributeId = 1)
		@FloatHandler(attributeId = 1)
		private Object field4;

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Fields with a handler and one default should pass.")
		@StringHandler(attributeId = 1)
		@DefaultToString("something")
		private Object field5;

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Fields with a handler and multiple defaults should fail.")
		@StringHandler(attributeId = 1)
		@DefaultToString("something")
		@DefaultToStringResource(1)
		private Object field6;

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Fields with a handler and multiple defaults should fail.")
		@FloatHandler(attributeId = 1)
		@DefaultToFloat(1F)
		@DefaultToFractionResource(resId = 1)
		@DefaultToString("something")
		private Object field7;

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Fields with one default and no handler should fail.")
		@DefaultToDimension(value = 1, unit = DP)
		private Object field8;

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Fields with multiple defaults and no handler should fail.")
		@DefaultToBoolean(false)
		@DefaultToString("something")
		private Object field9;

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with no annotations should pass.")
		private void method1() {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with just one value handler should pass.")
		@StringHandler(attributeId = 1)
		private void method2(Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with multiple value handlers should fail.")
		@StringHandler(attributeId = 1)
		@BooleanHandler(attributeId = 1)
		private void method3(Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with multiple value handlers should fail.")
		@StringHandler(attributeId = 1)
		@BooleanHandler(attributeId = 1)
		@FloatHandler(attributeId = 1)
		private void method4(Object o) {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with just one call handler should pass.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method5() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with multiple call handlers should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		@SpecificEnumHandler(attributeId = 1, ordinal = 1)
		private void method6() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler and a value handler should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		@StringHandler(attributeId = 1)
		private void method7() {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a value handler and one default should pass.")
		@BooleanHandler(attributeId = 1)
		@DefaultToBoolean(false)
		private void method8(Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler and multiple defaults should fail.")
		@StringHandler(attributeId = 1)
		@DefaultToString("something")
		@DefaultToStringResource(1)
		private void method9(Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler and multiple defaults should fail.")
		@StringHandler(attributeId = 1)
		@DefaultToString("something")
		@DefaultToStringResource(1)
		@DefaultToBoolean(false)
		private void method10(Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler and one default should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		@DefaultToBoolean(false)
		private void method11() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler and multiple defaults should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		@DefaultToString("something")
		@DefaultToStringResource(1)
		private void method12() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler and multiple defaults should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		@DefaultToString("something")
		@DefaultToStringResource(1)
		@DefaultToBoolean(false)
		private void method13() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with one default and no handler should fail.")
		@DefaultToBoolean(true)
		private void method14() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with multiple defaults and no handler should fail.")
		@DefaultToString("something")
		@DefaultToNull
		private void method15() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with multiple defaults and no handler should fail.")
		@DefaultToString("something")
		@DefaultToNull
		@DefaultToEnumConstant(enumClass = TestEnum.class, ordinal = 1)
		private void method16() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler and no arguments should fail.")
		@DimensionHandler(attributeId = 1)
		private void method17() {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a value handler and one non-use argument should " +
						"pass.")
		@FloatHandler(attributeId = 1)
		private void method18(Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler and one use argument should fail.")
		@BooleanHandler(attributeId = 1)
		private void method19(@UseBoolean(false) Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler and two non-use arguments should " +
						"fail.")
		@BooleanHandler(attributeId = 1)
		private void method20(Object o1, Object o2) {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a value handler, one non-use argument and one use " +
						"argument should pass.")
		@BooleanHandler(attributeId = 1)
		private void method21(@UseByte(1) Object o1, Object o2) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler and two use arguments should fail.")
		@BooleanHandler(attributeId = 1)
		private void method22(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler, two non-use arguments and one " +
						"use argument should fail.")
		@BooleanHandler(attributeId = 1)
		private void method23(Object o1, @UseDouble(1) Object o2, Object o3) {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a value handler, one non-use arguments and two " +
						"use arguments should pass.")
		@BooleanHandler(attributeId = 1)
		private void method24(Object o1, @UseFloat(1F) Object o2, @UseChar('A') Object o3) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a value handler and three use arguments should " +
						"fail.")
		@BooleanHandler(attributeId = 1)
		private void method25(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a call handler and no arguments should pass.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method26() {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler and one non-use argument should " +
						"fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method27(Object o) {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a call handler and one use argument should pass.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method28(@UseBoolean(false) Object o) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler and two non-use arguments should " +
						"fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method29(Object o1, Object o2) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler, one non-use argument and one use " +
						"argument should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method30(@UseByte(1) Object o1, Object o2) {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a call handler and two use arguments should pass.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method31(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler, two non-use arguments and one " +
						"use argument should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method32(Object o1, @UseDouble(2.0) Object o2, Object o3) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with a call handler, one non-use arguments and two " +
						"use argument should fail.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method33(Object o1, @UseFloat(2F) Object o2, @UseChar('A') Object o3) {}

		@ValidationTestTarget(
				isValid = true,
				failureMessage = "Methods with a call handler and three use arguments should pass.")
		@FlagHandler(attributeId = 1, handledFlags = 1)
		private void method34(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with one use argument but no handler should fail.")
		private void method35(@UseLong(1) Object o1, Object o2) {}

		@ValidationTestTarget(
				isValid = false,
				failureMessage = "Methods with multiple use argument but no handler should fail.")
		private void method36(@UseBoolean(false) Object o1, @UseString("something") Object o2) {}
	}

	private enum TestEnum {}

	@Target({METHOD, FIELD})
	@Retention(RUNTIME)
	public @interface ValidationTestTarget {
		boolean isValid();

		String failureMessage();
	}
}