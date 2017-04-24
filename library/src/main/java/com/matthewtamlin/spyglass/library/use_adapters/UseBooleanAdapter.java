package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with UseBoolean annotations.
 */
@Tested(testMethod = "automated")
public class UseBooleanAdapter implements UseAdapter<Boolean, UseBoolean> {
	@Override
	public Boolean getValue(final UseBoolean annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.value();
	}
}