package com.matthewtamlin.spyglass.processors.validation.test_validator;

import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.StringHandler;

public class Data {
	@Target(isValid = true)
	private void method1() {}

	@Target(isValid = true)
	@StringHandler(attributeId = 1)
	private void method2(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@BooleanHandler(attributeId = 1)
	private void method3(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@BooleanHandler(attributeId = 1)
	@FloatHandler(attributeId = 1)
	private void method4(Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method5() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@SpecificEnumHandler(attributeId = 1, ordinal = 1)
	private void method6() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@StringHandler(attributeId = 1)
	private void method7() {}

	@Target(isValid = true)
	@BooleanHandler(attributeId = 1)
	@DefaultToBoolean(false)
	private void method8(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	private void method9(Object o) {}

	@Target(isValid = false)
	@StringHandler(attributeId = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	@DefaultToBoolean(false)
	private void method10(Object o) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToBoolean(false)
	private void method11() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	private void method12() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	@DefaultToBoolean(false)
	private void method13() {}

	@Target(isValid = false)
	@DefaultToBoolean(true)
	private void method14() {}

	@Target(isValid = false)
	@DefaultToString("something")
	@DefaultToNull
	private void method15() {}

	@Target(isValid = false)
	@DefaultToString("something")
	@DefaultToNull
	@DefaultToEnumConstant(enumClass = Enum.class, ordinal = 1)
	private void method16() {}

	@Target(isValid = false)
	@DimensionHandler(attributeId = 1)
	private void method17() {}

	@Target(isValid = true)
	@FloatHandler(attributeId = 1)
	private void method18(Object o) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	private void method19(@UseBoolean(false) Object o) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	private void method20(Object o1, Object o2) {}

	@Target(isValid = true)
	@BooleanHandler(attributeId = 1)
	private void method21(@UseByte(1) Object o1, Object o2) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	private void method22(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	private void method23(Object o1, @UseDouble(1) Object o2, Object o3) {}

	@Target(isValid = true)
	@BooleanHandler(attributeId = 1)
	private void method24(Object o1, @UseFloat(1F) Object o2, @UseChar('A') Object o3) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	private void method25(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method26() {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method27(Object o) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method28(@UseBoolean(false) Object o) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method29(Object o1, Object o2) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method30(@UseByte(1) Object o1, Object o2) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method31(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method32(Object o1, @UseDouble(2.0) Object o2, Object o3) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method33(Object o1, @UseFloat(2F) Object o2, @UseChar('A') Object o3) {}

	@Target(isValid = true)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method34(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

	@Target(isValid = false)
	@BooleanHandler(attributeId = 1)
	private void method35(@UseInt(1) @UseBoolean(true) Object o1, Object o2) {}

	@Target(isValid = false)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method36(@UseInt(1) @UseBoolean(true) @UseString("something") Object o1) {}
}