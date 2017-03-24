package com.matthewtamlin.spyglass.library;

import static com.matthewtamlin.spyglass.library.DimensionUnit.PT;

public @interface BindDimension {
	int annotationId();

	DimensionUnit dimensionUnit() default PT;

	boolean ignoreIfAttributeMissing() default false;

	int defaultValue() default 0;
}