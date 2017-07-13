package com.matthewtamlin.spyglass.processor.mirror_helpers.annotation_mirror_helper;

public @interface SomeAnnotationWithValue {
	public static final String DEFAULT_VALUE = "default value";

	String value() default DEFAULT_VALUE;
}