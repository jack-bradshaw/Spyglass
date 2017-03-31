package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseShort;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseShortAdapter implements UseAdapter<Short, UseShort> {
	@Override
	public Short getValue(final UseShort annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return annotation.value();
	}
}