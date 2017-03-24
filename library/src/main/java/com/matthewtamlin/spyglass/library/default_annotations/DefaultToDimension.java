package com.matthewtamlin.spyglass.library.default_annotations;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;

public @interface DefaultToDimension {
	int value();

	DimensionUnit unit();
}