package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

public class DefaultToBooleanProcessor implements DefaultAdapter<Boolean, DefaultToBoolean> {
	@Override
	public Boolean process(final DefaultToBoolean annotation, final Context context) {
		return annotation.value();
	}
}