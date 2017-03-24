package com.matthewtamlin.spyglass.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindFraction {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	int baseMultiplier() default 1;

	int parentMultiplier() default 1;

	float defaultValue() default 0f;

	int defaultResourceId() default 0;
}