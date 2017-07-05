package com.matthewtamlin.spyglass.processor.annotation_info.use_anno_info;

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
	@ElementId("with use boolean")
	public void test(@UseBoolean(true) boolean b) {}

	@ElementId("with use byte")
	public void test(@UseByte(1) byte b) {}

	@ElementId("with use char")
	public void test(@UseChar(1) char c) {}

	@ElementId("with use double")
	public void test(@UseDouble(1) double d) {}

	@ElementId("with use float")
	public void test(@UseFloat(1F) float f) {}

	@ElementId("with use int")
	public void test(@UseInt(1) int i) {}

	@ElementId("with use long")
	public void test(@UseLong(1) long l) {}

	@ElementId("with use null")
	public void test(@UseNull Object o) {}

	@ElementId("with use short")
	public void test(@UseShort(1) short s) {}

	@ElementId("with use string")
	public void test(@UseString("") String s) {}
}