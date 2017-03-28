package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class DefaultToIntegerResourceAdapter
		implements DefaultAdapter<Integer, DefaultToIntegerResource> {

	@Override
	public Integer getDefault(final DefaultToIntegerResource annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return context.getResources().getInteger(annotation.value());
	}
}