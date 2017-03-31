package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseFloatAdapter implements UseAdapter<Float, UseFloat> {
	@Override
	public Float getValue(final UseFloat annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return annotation.value();
	}
}