package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseString;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseStringAdapter implements UseAdapter<String, UseString> {
	@Override
	public String getValue(final UseString annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return annotation.value();
	}
}