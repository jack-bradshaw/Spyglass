package com.matthewtamlin.spyglass.processors.annotation_utils.annotation_mirror_util;

import com.matthewtamlin.java_compiler_utilities.element_supplier.ElementId;

public class Data {
	@ElementId("get annotation mirror: without annotation")
	public String fieldWithoutAnnotation;

	@ElementId("get annotation mirror: with annotation")
	@AnnotationWithValues
	public String fieldWithAnnotation;
}