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
import com.matthewtamlin.spyglass.library.use_annotations.UseInt;
import com.matthewtamlin.spyglass.library.use_annotations.UseLong;
import com.matthewtamlin.spyglass.library.util.SpyglassValidationException;
import com.matthewtamlin.spyglass.library.util.ValidationUtil;
import com.matthewtamlin.spyglass.library.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.library.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.library_tests.util.FieldHelper.FieldTag;
import com.matthewtamlin.spyglass.library_tests.util.MethodHelper.MethodTag;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static android.R.id.message;
import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class TestValidationUtil {
	private static final String FIELD_MESSAGE = "Expected validation of field %1$s to %2$s.";

	private static final String METHOD_MESSAGE = "Expected validation of method %1$s to %2$s.";

	public void testValidateField_usingFieldsOfTestClass() {
		for (final Field f : TestClass.class.getDeclaredFields()) {
			final boolean shouldPass = f.getAnnotation(ValidationTestTarget.class).isValid();
			final boolean doesPass = passesValidation(f);

			final String message = String.format(FIELD_MESSAGE, f, (shouldPass ? "pass" : "fail"));
			assertThat(message, shouldPass, is(doesPass));
		}
	}

	public void testValidateMethod_usingMethodsOfTestClass() {
		for (final Method m : TestClass.class.getDeclaredMethods()) {
			final boolean shouldPass = m.getAnnotation(ValidationTestTarget.class).isValid();
			final boolean doesPass = passesValidation(m);

			final String message = String.format(METHOD_MESSAGE, m, (shouldPass ? "pass" : "fail"));
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
		private Object field1;

		@BooleanHandler(attributeId = 2)
		private Object field2;

		@FloatHandler(attributeId = 3)
		@DimensionHandler(attributeId = 3)
		private Object field3;

		@StringHandler(attributeId = 4)
		@IntegerHandler(attributeId = 4)
		@FractionHandler(attributeId = 4)
		private Object field4;

		@BooleanHandler(attributeId = 5)
		@DefaultToBoolean(true)
		private Object field5;

		@DimensionHandler(attributeId = 6)
		@DefaultToInteger(6)
		@DefaultToDrawableResource(6)
		private Object field6;

		@StringHandler(attributeId = 7)
		@DefaultToEnumConstant(enumClass = TestEnum.class, ordinal = 0)
		@DefaultToBooleanResource(7)
		@DefaultToStringResource(7)
		private Object field7;

		@DefaultToColorStateListResource(1)
		private Object field8;

		private void method1() {}

		@BooleanHandler(attributeId = 2)
		private void method2(Object o) {}

		@StringHandler(attributeId = 3)
		@FloatHandler(attributeId = 3)
		private void method3(Object o) {}

		@FractionHandler(attributeId = 4)
		@DimensionHandler(attributeId = 4)
		@IntegerHandler(attributeId = 4)
		private void method4(Object o) {}

		@FractionHandler(attributeId = 5)
		@DefaultToFloat(5)
		private void method5(Object o) {}

		@BooleanHandler(attributeId = 6)
		@DefaultToColorResource(6)
		@DefaultToNull
		private void method6(Object o) {}

		@BooleanHandler(attributeId = 6)
		@DefaultToColorResource(6)
		@DefaultToDimensionResource(6)
		@DefaultToDimension(value = 10, unit = DP)
		private void method7(Object o) {}

		@DefaultToString("something")
		private void method8(Object o) {}

		@IntegerHandler(attributeId = 9)
		private void method9() {}

		@DimensionHandler(attributeId = 10)
		private void method10(Object o) {}

		@FractionHandler(attributeId = 11)
		private void method11(@UseBoolean(true) Object o) {}

		@StringHandler(attributeId = 12)
		private void method12(Object o1, Object o2, Object o3) {}

		@BooleanHandler(attributeId = 13)
		private void method13(@UseChar('A') Object o1, Object o2, @UseBoolean(true) Object o3) {}

		@FractionHandler(attributeId = 14)
		private void method14(
				@UseLong(0L) Object o1,
				@UseInt(1) Object o2,
				@UseByte(0) Object o3) {}

		private void method15() {}

		private void method16(Object o) {}

		private void method17(@UseBoolean(true) Object o) {}

		private void method18(Object o1, Object o2, Object o3) {}

		private void method19(@UseChar('A') Object o1, Object o2, @UseBoolean(true) Object o3) {}

		private void method20(
				@UseLong(0L) Object o1,
				@UseInt(1) Object o2,
				@UseByte(0) Object o3) {}

		@BooleanHandler(attributeId = 21)
		private void method21(@UseLong(0L) @UseBoolean(true) Object o) {}

		private void method22(@UseBoolean(true) Object o) {}
	}

	private enum TestEnum {}

	@Target({METHOD, FIELD})
	@Retention(RUNTIME)
	public @interface ValidationTestTarget {
		boolean isValid();

		String reason();
	}
}