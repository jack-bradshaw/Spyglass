package com.matthewtamlin.spyglass.library;

public @interface BindString {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	String defaultValue() default "";
}
