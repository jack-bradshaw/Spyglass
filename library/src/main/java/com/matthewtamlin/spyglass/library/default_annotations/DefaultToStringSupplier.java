package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.Supplier;

public @interface DefaultToStringSupplier {
	Class<? extends Supplier<String>> value();
}
