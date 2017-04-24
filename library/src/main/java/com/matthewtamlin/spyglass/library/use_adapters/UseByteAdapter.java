package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.use_annotations.UseByte;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with UseByte annotations.
 */
@Tested(testMethod = "automated")
public class UseByteAdapter implements UseAdapter<Byte, UseByte> {
	@Override
	public Byte getValue(final UseByte annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.value();
	}
}