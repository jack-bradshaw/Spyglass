package com.matthewtamlin.spyglass.processors.code_generation.invocation_literal_generator;

import com.matthewtamlin.java_compiler_utilities.element_supplier.ElementId;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseString;

public class Data {
	public static final String PLACEHOLDER = "value";

	@ElementId("no use annotation without extra arg")
	public void method() {}

	@ElementId("no use annotation with extra arg")
	public void method(Object o) {}

	@ElementId("boolean without extra arg")
	public void method(@UseBoolean(true) boolean b) {}

	@ElementId("boolean with extra arg")
	public void method(@UseBoolean(false) boolean b, Object o) {}

	@ElementId("byte without extra arg")
	public void method(@UseByte(100) byte b) {}

	@ElementId("byte with extra arg")
	public void method(@UseByte(-100) byte b, Object o) {}

	@ElementId("char without extra arg")
	public void method(@UseChar('\u1D11') char c) {}

	@ElementId("char with extra arg")
	public void method(Object o, @UseChar('\uF2FF') char c) {}

	@ElementId("double without extra arg")
	public void method(@UseDouble(100.100) double d) {}

	@ElementId("double with extra arg")
	public void method(@UseDouble(-100.100) double d, Object o) {}

	@ElementId("float without extra arg")
	public void method(@UseFloat(100.100F) float f) {}

	@ElementId("float with extra arg")
	public void method(@UseFloat(-100.100F) float f, Object o) {}

	@ElementId("int without extra arg")
	public void method(@UseInt(100) int i) {}

	@ElementId("int with extra arg")
	public void method(@UseInt(-100) int i, Object o) {}

	@ElementId("long without extra arg")
	public void method(@UseLong(100L) long l) {}

	@ElementId("long with extra arg")
	public void method(@UseLong(-100L) long l, Object o) {}

	@ElementId("null without extra arg")
	public void method(@UseNull Boolean b) {}

	@ElementId("null with extra arg")
	public void method(@UseNull Boolean b, Object o) {}

	@ElementId("short without extra arg")
	public void method(@UseShort(100) short s) {}

	@ElementId("short with extra arg")
	public void method(@UseShort(-100) short s, Object o) {}

	@ElementId("string without extra arg")
	public void method(@UseString("hello world \\ __ \"quote\" // ! ©  ") String s) {}

	@ElementId("string with extra arg")
	public void method(@UseString("hello world \\ __ \"quote\" // ! ©  ") String s, Object o) {}
}