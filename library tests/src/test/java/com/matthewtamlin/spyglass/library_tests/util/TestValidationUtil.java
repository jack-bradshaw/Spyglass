package com.matthewtamlin.spyglass.library_tests.util;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFloat;
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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.DP;

@RunWith(JUnit4.class)
public class TestValidationUtil {
	@SuppressWarnings("unused")
	public class TestClass {
		@FieldTag(1)
		private Object field1;

		@FieldTag(2)
		@BooleanHandler(attributeId = 2)
		private Object field2;

		@FieldTag(3)
		@FloatHandler(attributeId = 3)
		@DimensionHandler(attributeId = 3)
		private Object field3;

		@FieldTag(4)
		@StringHandler(attributeId = 4)
		@IntegerHandler(attributeId = 4)
		@FractionHandler(attributeId = 4)
		private Object field4;

		@FieldTag(5)
		@BooleanHandler(attributeId = 5)
		@DefaultToBoolean(true)
		private Object field5;

		@FieldTag(6)
		@DimensionHandler(attributeId = 6)
		@DefaultToInteger(6)
		@DefaultToDrawableResource(6)
		private Object field6;

		@FieldTag(7)
		@StringHandler(attributeId = 7)
		@DefaultToEnumConstant(enumClass = TestEnum.class, ordinal = 0)
		@DefaultToBooleanResource(7)
		@DefaultToStringResource(7)
		private Object field7;

		@FieldTag(8)
		@DefaultToColorStateListResource(1)
		private Object field8;

		@MethodTag(1)
		private void method1() {}

		@MethodTag(2)
		@BooleanHandler(attributeId = 2)
		private void method2(Object o) {}

		@MethodTag(3)
		@StringHandler(attributeId = 3)
		@FloatHandler(attributeId = 3)
		private void method3(Object o) {}

		@MethodTag(4)
		@FractionHandler(attributeId = 4)
		@DimensionHandler(attributeId = 4)
		@IntegerHandler(attributeId = 4)
		private void method4(Object o) {}

		@MethodTag(5)
		@FractionHandler(attributeId = 5)
		@DefaultToFloat(5)
		private void method5(Object o) {}

		@MethodTag(6)
		@BooleanHandler(attributeId = 6)
		@DefaultToColorResource(6)
		@DefaultToNull
		private void method6(Object o) {}

		@MethodTag(7)
		@BooleanHandler(attributeId = 6)
		@DefaultToColorResource(6)
		@DefaultToDimensionResource(6)
		@DefaultToDimension(value = 10, unit = DP)
		private void method7(Object o) {}

		@MethodTag(8)
		@DefaultToString("something")
		private void method8(Object o) {}

		@MethodTag(9)
		@IntegerHandler(attributeId = 9)
		private void method9() {}

		@MethodTag(10)
		@DimensionHandler(attributeId = 10)
		private void method10(Object o) {}

		@MethodTag(11)
		@FractionHandler(attributeId = 11)
		private void method11(@UseBoolean(true) Object o) {}

		@MethodTag(12)
		@StringHandler(attributeId = 12)
		private void method12(Object o1, Object o2, Object o3) {}

		@MethodTag(13)
		@BooleanHandler(attributeId = 13)
		private void method13(@UseChar('A') Object o1, Object o2, @UseBoolean(true) Object o3) {}

		@MethodTag(14)
		@FractionHandler(attributeId = 14)
		private void method14(
				@UseLong(0L) Object o1,
				@UseInt(1) Object o2,
				@UseByte(0) Object o3) {}

		@MethodTag(15)
		private void method15() {}

		@MethodTag(16)
		private void method16(Object o) {}

		@MethodTag(17)
		private void method17(@UseBoolean(true) Object o) {}

		@MethodTag(18)
		private void method18(Object o1, Object o2, Object o3) {}

		@MethodTag(19)
		private void method19(@UseChar('A') Object o1, Object o2, @UseBoolean(true) Object o3) {}

		@MethodTag(20)
		private void method20(
				@UseLong(0L) Object o1,
				@UseInt(1) Object o2,
				@UseByte(0) Object o3) {}

		@MethodTag(21)
		@BooleanHandler(attributeId = 21)
		private void method21(@UseLong(0L) @UseBoolean(true) Object o) {}

		@MethodTag(22)
		private void method22(@UseBoolean(true) Object o) {}
	}

	private enum TestEnum {
		CONST1
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface ExpectedToPassValidation {
		boolean value();
	}
}