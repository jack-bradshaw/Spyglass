package com.matthewtamlin.spyglass.library;

public @interface BindFloat {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	float defaultValue() default 0f;
}
