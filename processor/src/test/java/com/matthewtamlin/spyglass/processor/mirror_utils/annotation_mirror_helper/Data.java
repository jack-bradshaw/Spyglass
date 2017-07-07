package com.matthewtamlin.spyglass.processor.mirror_utils.annotation_mirror_helper;

import com.matthewtamlin.avatar.element_supplier.ElementId;

public class Data {
	public static final String SPECIFIED_VALUE = "specified value";

	@ElementId("get annotation mirror: without annotation")
	public String field1;

	@ElementId("get annotation mirror: with annotation")
	@SomeAnnotationWithValue
	public String field2;

	@ElementId("get annotation value ignoring defaults: no value")
	@SomeAnnotationWithValue()
	public Object field3;

	@ElementId("get annotation value ignoring defaults: with value")
	@SomeAnnotationWithValue(value = SPECIFIED_VALUE)
	public Object field4;

	@ElementId("get annotation value with defaults: no value")
	@SomeAnnotationWithValue()
	public Object field5;

	@ElementId("get annotation value with defaults: with value")
	@SomeAnnotationWithValue(value = SPECIFIED_VALUE)
	public Object field6;
}