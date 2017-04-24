package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.use_annotations.UseNull;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with UseNull annotations.
 */
@Tested(testMethod = "automated")
public class UseNullAdapter implements UseAdapter<Void, UseNull> {
	@Override
	public Void getValue(final UseNull annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return null;
	}
}