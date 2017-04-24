package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.use_annotations.UseInt;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with UseInt annotations.
 */
@Tested(testMethod = "automated")
public class UseIntAdapter implements UseAdapter<Integer, UseInt> {
	@Override
	public Integer getValue(final UseInt annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.value();
	}
}