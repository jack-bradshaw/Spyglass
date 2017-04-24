package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.use_annotations.UseChar;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with UseChar annotations.
 */
@Tested(testMethod = "automated")
public class UseCharAdapter implements UseAdapter<Character, UseChar> {
	@Override
	public Character getValue(final UseChar annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.value();
	}
}