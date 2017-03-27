package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;

public class DefaultToDimensionResourceAdapter
		implements DefaultAdapter<Float, DefaultToDimensionResource> {

	@Override
	public Float process(final DefaultToDimensionResource annotation, final Context context) {
		return context.getResources().getDimension(annotation.value());
	}
}