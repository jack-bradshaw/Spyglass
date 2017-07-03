package com.matthewtamlin.spyglass.processor.code_generation.do_invocation_generator;

import com.matthewtamlin.avatar.element_supplier.ElementId;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;

public class Data {
	@ElementId("call handler, no args")
	@SpecificEnumHandler(attributeId = 0, ordinal = 0)
	public void method() {}

	@ElementId("call handler, one arg")
	@SpecificEnumHandler(attributeId = 0, ordinal = 0)
	public void method(@UseFloat(1F) float f) {}

	@ElementId("call handler, multiple args")
	@SpecificFlagHandler(attributeId = 0, handledFlags = 0)
	public void method(
			@UseBoolean(true) boolean b1,
			@UseByte(100) byte b2,
			@UseChar('a') char c,
			@UseDouble(1.0) double d,
			@UseFloat(1.0F) float f,
			@UseInt(1) int i,
			@UseLong(1L) long l,
			@UseNull Object o,
			@UseShort(1) Short s1,
			@UseString("") String s2) {}

	@ElementId("value handler, primitive number arg")
	@BooleanHandler(attributeId = 0)
	public void method(int i) {}

	@ElementId("value handler, primitive non-number arg")
	@StringHandler(attributeId = 0)
	public void method(boolean b) {}

	@ElementId("value handler, primitive char arg")
	@FloatHandler(attributeId = 0)
	public void method(char c) {}

	@ElementId("value handler, object number arg")
	@EnumOrdinalHandler(attributeId = 0)
	public void method(Integer i) {}

	@ElementId("value handler, object non-number arg")
	@ColorHandler(attributeId = 0)
	public void method(String s) {}

	@ElementId("value handler, object character arg")
	@DimensionHandler(attributeId = 0)
	public void method(Character c) {}

	@ElementId("value handler, multiple args")
	@DrawableHandler(attributeId = 0)
	public void method(
			@UseBoolean(true) boolean b1,
			String nonUseArg,
			@UseByte(100) byte b2,
			@UseChar('a') char c,
			@UseDouble(1.0) double d,
			@UseFloat(1.0F) float f,
			@UseInt(1) int i,
			@UseLong(1L) long l,
			@UseNull Object o,
			@UseShort(1) Short s1,
			@UseString("") String s2) {}
}