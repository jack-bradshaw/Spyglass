package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToBooleanResource annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToBooleanResourceAdapter
		implements DefaultAdapter<Boolean, DefaultToBooleanResource> {

	@Override
	public Boolean getDefault(final DefaultToBooleanResource annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return context.getResources().getBoolean(annotation.value());
	}
}