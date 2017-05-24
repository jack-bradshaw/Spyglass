package com.matthewtamlin.spyglass.processors.annotation_utils.annotation_mirror_util;

import com.matthewtamlin.java_compiler_utilities.element_supplier.ElementId;

public class Data {
	@ElementId("without annotation")
	public String fieldWithoutAnnotation;

	@ElementId("with annotation")
	@SomeAnnotation
	public String fieldWithAnnotation;
}