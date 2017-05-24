package com.matthewtamlin.spyglass.processors.annotation_utils.annotation_mirror_util;

public @interface AnnotationWithValues {
	String value() default "default value";
}