package com.matthewtamlin.spyglass.library;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static com.matthewtamlin.spyglass.library.DimensionUnit.PT;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindDimension {
	int annotationId();

	DimensionUnit dimensionUnit() default PT;

	boolean ignoreIfAttributeMissing() default false;

	int defaultValue() default 0;

	int defaultResourceId() default 0;
}