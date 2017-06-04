package com.matthewtamlin.spyglass.processors.annotation_utils.annotation_mirror_util;

public @interface AnnotationWithValues {
	public static final String DEFAULT_VALUE = "default value";

	String value() default DEFAULT_VALUE;
}