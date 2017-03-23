package com.matthewtamlin.spyglass.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static com.matthewtamlin.spyglass.library.IgnoreMode.NEVER_IGNORE;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindColor {
	int annotationId();

	IgnoreMode ignoreMode() default NEVER_IGNORE;

	int defaultValue() default 0;
}