package com.matthewtamlin.spyglass.processor.code_generation.get_placeholder_method_generator;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.annotations.annotations.use_annotations.UseString;

public class Data {
	public void method(@UseBoolean(true) @ElementId("boolean") boolean b) {}

	public void method(@UseByte(1) @ElementId("byte") byte b) {}

	public void method(@UseChar('a') @ElementId("char") char c) {}

	public void method(@UseDouble(10.2) @ElementId("double") double d) {}

	public void method(@UseFloat(20.8F) @ElementId("float") float f) {}

	public void method(@UseInt(9) @ElementId("int") int i) {}

	public void method(@UseLong(9L) @ElementId("long") long l) {}

	public void method(@UseNull @ElementId("null") Object o) {}

	public void method(@UseShort(2) @ElementId("short") short s) {}

	public void method(@UseString("") @ElementId("string") String s) {}

	public void method(@ElementId("none") Void v) {}
}