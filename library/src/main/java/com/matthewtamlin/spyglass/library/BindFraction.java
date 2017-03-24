package com.matthewtamlin.spyglass.library;

public @interface BindFraction {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	int baseMultiplier() default 1;

	int parentMultiplier() default 1;

	float defaultValue() default 0f;
}