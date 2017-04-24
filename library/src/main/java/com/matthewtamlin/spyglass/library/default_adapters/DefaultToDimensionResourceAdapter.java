package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToDimensionResource annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToDimensionResourceAdapter
		implements DefaultAdapter<Float, DefaultToDimensionResource> {

	@Override
	public Float getDefault(final DefaultToDimensionResource annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return context.getResources().getDimension(annotation.value());
	}
}