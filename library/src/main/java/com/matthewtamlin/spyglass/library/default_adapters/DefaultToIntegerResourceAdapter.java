package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerResource;

public class DefaultToIntegerResourceAdapter
		implements DefaultAdapter<Integer, DefaultToIntegerResource> {

	@Override
	public Integer getDefault(final DefaultToIntegerResource annotation, final Context context) {
		return context.getResources().getInteger(annotation.value());
	}
}