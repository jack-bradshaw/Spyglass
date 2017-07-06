package com.matthewtamlin.spyglass.processor.mirror_utils.annotation_mirror_util;

public @interface SomeAnnotationWithValue {
	public static final String DEFAULT_VALUE = "default value";

	String value() default DEFAULT_VALUE;
}