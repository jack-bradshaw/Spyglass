package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToColorResource annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToColorResourceAdapter
		implements DefaultAdapter<Integer, DefaultToColorResource> {

	@Override
	public Integer getDefault(final DefaultToColorResource annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return ContextCompat.getColor(context, annotation.value());
	}
}