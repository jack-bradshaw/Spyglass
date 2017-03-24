package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.core.Supplier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToDimensionSupplier {
	Class<? extends Supplier<Integer>> valueSupplier();

	Class<? extends Supplier<DimensionUnit>> unitSupplier();
}