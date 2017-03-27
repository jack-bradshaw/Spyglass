package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerResource;

public class DefaultToIntegerResourceAdapter
		implements DefaultAdapter<Integer, DefaultToIntegerResource> {

	@Override
	public Integer process(final DefaultToIntegerResource annotation, final Context context) {
		return context.getResources().getInteger(annotation.value());
	}
}