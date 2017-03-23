package com.matthewtamlin.spyglass.library;

import static com.matthewtamlin.spyglass.library.DimensionUnit.PT;
import static com.matthewtamlin.spyglass.library.IgnoreMode.NEVER_IGNORE;

public @interface BindDimension {
	int annotationId();

	DimensionUnit dimensionUnit() default PT;

	IgnoreMode ignoreMode() default NEVER_IGNORE;

	int defaultValue() default 0;
}