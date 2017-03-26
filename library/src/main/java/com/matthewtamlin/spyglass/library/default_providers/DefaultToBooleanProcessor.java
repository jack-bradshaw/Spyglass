package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

public class DefaultToBooleanProcessor
		implements DefaultProcessor<Boolean, DefaultToBoolean> {
	@Override
	public Boolean get(final Context context, final DefaultToBoolean annotation) {
		return annotation.value();
	}
}
