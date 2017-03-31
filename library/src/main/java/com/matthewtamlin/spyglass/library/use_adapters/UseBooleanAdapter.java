package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseBooleanAdapter implements UseAdapter<Boolean, UseBoolean> {
	@Override
	public Boolean getValue(final UseBoolean annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return annotation.value();
	}
}