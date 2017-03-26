package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

public class DefaultToBooleanProcessor implements DefaultProcessor<Boolean, DefaultToBoolean> {
	@Override
	public Boolean process(final DefaultToBoolean annotation, final Context context) {
		return annotation.value();
	}
}