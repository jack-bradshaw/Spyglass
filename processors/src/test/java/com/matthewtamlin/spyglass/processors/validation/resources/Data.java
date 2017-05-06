package com.matthewtamlin.spyglass.processors.validation.resources;

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
	@Target(
			isValid = true,
			failureMessage = "Methods with no annotations should pass.")
	private void method1() {}

	@Target(
			isValid = true,
			failureMessage = "Methods with just one value handler should pass.")
	@StringHandler(attributeId = 1)
	private void method2(Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple value handlers should fail.")
	@StringHandler(attributeId = 1)
	@BooleanHandler(attributeId = 1)
	private void method3(Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple value handlers should fail.")
	@StringHandler(attributeId = 1)
	@BooleanHandler(attributeId = 1)
	@FloatHandler(attributeId = 1)
	private void method4(Object o) {}

	@Target(
			isValid = true,
			failureMessage = "Methods with just one call handler should pass.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method5() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple call handlers should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@SpecificEnumHandler(attributeId = 1, ordinal = 1)
	private void method6() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler and a value handler should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@StringHandler(attributeId = 1)
	private void method7() {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a value handler and one default should pass.")
	@BooleanHandler(attributeId = 1)
	@DefaultToBoolean(false)
	private void method8(Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler and multiple defaults should fail.")
	@StringHandler(attributeId = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	private void method9(Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler and multiple defaults should fail.")
	@StringHandler(attributeId = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	@DefaultToBoolean(false)
	private void method10(Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler and one default should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToBoolean(false)
	private void method11() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler and multiple defaults should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	private void method12() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler and multiple defaults should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	@DefaultToString("something")
	@DefaultToStringResource(1)
	@DefaultToBoolean(false)
	private void method13() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with one default and no handler should fail.")
	@DefaultToBoolean(true)
	private void method14() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple defaults and no handler should fail.")
	@DefaultToString("something")
	@DefaultToNull
	private void method15() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple defaults and no handler should fail.")
	@DefaultToString("something")
	@DefaultToNull
	@DefaultToEnumConstant(enumClass = Enum.class, ordinal = 1)
	private void method16() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler and no arguments should fail.")
	@DimensionHandler(attributeId = 1)
	private void method17() {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a value handler and one non-use argument should " +
					"pass.")
	@FloatHandler(attributeId = 1)
	private void method18(Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler and one use argument should fail.")
	@BooleanHandler(attributeId = 1)
	private void method19(@UseBoolean(false) Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler and two non-use arguments should " +
					"fail.")
	@BooleanHandler(attributeId = 1)
	private void method20(Object o1, Object o2) {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a value handler, one non-use argument and one use " +
					"argument should pass.")
	@BooleanHandler(attributeId = 1)
	private void method21(@UseByte(1) Object o1, Object o2) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler and two use arguments should fail.")
	@BooleanHandler(attributeId = 1)
	private void method22(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler, two non-use arguments and one " +
					"use argument should fail.")
	@BooleanHandler(attributeId = 1)
	private void method23(Object o1, @UseDouble(1) Object o2, Object o3) {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a value handler, one non-use arguments and two " +
					"use arguments should pass.")
	@BooleanHandler(attributeId = 1)
	private void method24(Object o1, @UseFloat(1F) Object o2, @UseChar('A') Object o3) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a value handler and three use arguments should " +
					"fail.")
	@BooleanHandler(attributeId = 1)
	private void method25(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a call handler and no arguments should pass.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method26() {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler and one non-use argument should " +
					"fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method27(Object o) {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a call handler and one use argument should pass.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method28(@UseBoolean(false) Object o) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler and two non-use arguments should " +
					"fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method29(Object o1, Object o2) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler, one non-use argument and one use " +
					"argument should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method30(@UseByte(1) Object o1, Object o2) {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a call handler and two use arguments should pass.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method31(@UseByte(1) Object o1, @UseLong(1) Object o2) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler, two non-use arguments and one " +
					"use argument should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method32(Object o1, @UseDouble(2.0) Object o2, Object o3) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with a call handler, one non-use argument and two " +
					"use arguments should fail.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method33(Object o1, @UseFloat(2F) Object o2, @UseChar('A') Object o3) {}

	@Target(
			isValid = true,
			failureMessage = "Methods with a call handler and three use arguments should pass.")
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method34(@UseNull Object o1, @UseChar('A') Object o2, @UseInt(1) Object o3) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with one use argument but no handler should fail.")
	private void method35(@UseLong(1) Object o1, Object o2) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple use arguments but no handler should fail.")
	private void method36(@UseBoolean(false) Object o1, @UseString("something") Object o2) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple use annotations on a single parameter " +
					"should fail."
	)
	@BooleanHandler(attributeId = 1)
	private void method37(@UseInt(1) @UseBoolean(true) Object o1, Object o2) {}

	@Target(
			isValid = false,
			failureMessage = "Methods with multiple use annotations on a single parameter " +
					"should fail."
	)
	@SpecificFlagHandler(attributeId = 1, handledFlags = 1)
	private void method38(@UseInt(1) @UseBoolean(true) @UseString("something") Object o1) {}
}