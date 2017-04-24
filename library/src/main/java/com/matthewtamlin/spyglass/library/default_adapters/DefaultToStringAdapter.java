package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToString annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToStringAdapter implements DefaultAdapter<String, DefaultToString> {
	@Override
	public String getDefault(final DefaultToString annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return annotation.value();
	}
}