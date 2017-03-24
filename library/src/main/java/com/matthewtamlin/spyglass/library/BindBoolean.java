package com.matthewtamlin.spyglass.library;

public @interface BindBoolean {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	boolean defaultValue() default false;
}
