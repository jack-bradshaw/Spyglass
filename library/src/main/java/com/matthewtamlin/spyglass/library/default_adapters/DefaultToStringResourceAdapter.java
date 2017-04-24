package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToStringResource annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToStringResourceAdapter
		implements DefaultAdapter<String, DefaultToStringResource> {

	@Override
	public String getDefault(final DefaultToStringResource annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return context.getResources().getString(annotation.value());
	}
}