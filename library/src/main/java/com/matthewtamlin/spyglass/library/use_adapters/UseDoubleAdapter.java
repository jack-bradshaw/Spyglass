package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class UseDoubleAdapter implements UseAdapter<Double, UseDouble> {
	@Override
	public Double getValue(final UseDouble annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.value();
	}
}