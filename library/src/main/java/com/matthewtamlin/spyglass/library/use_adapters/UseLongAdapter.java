package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseLong;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseLongAdapter implements UseAdapter<Long, UseLong> {
	@Override
	public Long getValue(final UseLong annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return annotation.value();
	}
}