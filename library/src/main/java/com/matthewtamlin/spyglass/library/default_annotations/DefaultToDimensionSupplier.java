package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.core.Supplier;

public @interface DefaultToDimensionSupplier {
	Class<? extends Supplier<Integer>> valueSupplier();

	Class<? extends Supplier<DimensionUnit>> unitSupplier();
}
