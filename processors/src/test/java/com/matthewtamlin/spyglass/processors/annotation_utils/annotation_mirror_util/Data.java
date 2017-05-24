package com.matthewtamlin.spyglass.processors.annotation_utils.annotation_mirror_util;

import com.matthewtamlin.java_compiler_utilities.element_supplier.ElementId;

public class Data {
	@ElementId("get annotation mirror: without annotation")
	public String fieldWithoutAnnotation;

	@ElementId("get annotation mirror: with annotation")
	@AnnotationWithValues
	public String fieldWithAnnotation;

	@ElementId("get annotation value ignoring defaults: no value provided")
	@AnnotationWithValues()
	public Object field4;

	@ElementId("get annotation value ignoring defaults: value provided")
	@AnnotationWithValues(value ="specified value")
	public Object field5;

	@ElementId("get annotation value with defaults: no value provided")
	@AnnotationWithValues()
	public Object field6;

	@ElementId("get annotation value with defaults: value provided")
	@AnnotationWithValues(value ="specified value")
	public Object field7;
}