package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.Supplier;

public @interface DefaultToFloatSupplier {
	Class<? extends Supplier<Float>> value();
}