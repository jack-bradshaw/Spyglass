package com.matthewtamlin.spyglass.library;

public @interface BindInteger {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	int defaultValue() default 0;
}