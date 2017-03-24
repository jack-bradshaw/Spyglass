package com.matthewtamlin.spyglass.library.binder_annotations;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import static com.matthewtamlin.spyglass.library.core.DimensionUnit.PT;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface BindDimension {
	int annotationId();

	DimensionUnit dimensionUnit() default PT;
}