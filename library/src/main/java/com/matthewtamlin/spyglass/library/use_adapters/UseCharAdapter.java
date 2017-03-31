package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseChar;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseCharAdapter implements UseAdapter<Character, UseChar> {
	@Override
	public Character getValue(final UseChar annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return annotation.value();
	}
}