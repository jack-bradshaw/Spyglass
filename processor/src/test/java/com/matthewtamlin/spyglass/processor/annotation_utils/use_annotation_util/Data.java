package com.matthewtamlin.spyglass.processor.annotation_utils.use_annotation_util;

import com.matthewtamlin.avatar.element_supplier.ElementId;
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
	public void method(@ElementId("boolean") @UseBoolean(true) boolean b, Object o) {}

	public void method(Object o, @ElementId("byte") @UseByte(0) byte b) {}

	public void method(@ElementId("char") @UseChar(0) char c) {}

	public void method(@ElementId("double") @UseDouble(0) double d) {}

	public void method(@ElementId("float") @UseFloat(0) float f) {}

	public void method(@ElementId("int") @UseInt(0) int i) {}

	public void method(@ElementId("long") @UseLong(0) long l) {}

	public void method(@ElementId("null") @UseNull Object o) {}

	public void method(@ElementId("short") @UseShort(0) short s) {}

	public void method(@ElementId("string") @UseString("") String s) {}

	public void method(@ElementId("no use annotation") Object o1, Object o2) {}

	private enum PlaceholderEnum {}
}