package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class DefaultToBooleanAdapter implements DefaultAdapter<Boolean, DefaultToBoolean> {
	@Override
	public Boolean getDefault(final DefaultToBoolean annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.value();
	}
}