package com.matthewtamlin.spyglass.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindFloat {
	int annotationId();

	boolean ignoreIfAttributeMissing() default false;

	float defaultValue() default 0f;
}