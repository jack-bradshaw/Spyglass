package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBoolean;

public class DefaultToBooleanAdapter implements DefaultAdapter<Boolean, DefaultToBoolean> {
	@Override
	public Boolean process(final DefaultToBoolean annotation, final Context context) {
		return annotation.value();
	}
}