package com.matthewtamlin.spyglass.processor.code_generation.get_placeholder_method_generator;

import com.matthewtamlin.avatar.rules.ElementId;
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

public class Data {
	@ElementId("boolean")
	public void method(@UseBoolean(true) boolean b) {}

	@ElementId("byte")
	public void method(@UseByte(1) byte b) {}

	@ElementId("char")
	public void method(@UseChar('a') char c) {}

	@ElementId("double")
	public void method(@UseDouble(10.2) double d) {}

	@ElementId("float")
	public void method(@UseFloat(20.8F) float f) {}

	@ElementId("int")
	public void method(@UseInt(9) int i) {}

	@ElementId("long")
	public void method(@UseLong(9L) long l) {}

	@ElementId("null")
	public void method(@UseNull Object o) {}

	@ElementId("short")
	public void method(@UseShort(2) short s) {}

	@ElementId("string")
	public void method(@UseString("") String s) {}

	@ElementId("none")
	public void method(Void v) {}
}