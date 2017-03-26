package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;

public class DefaultToDimensionResourceProcessor
		implements DefaultProcessor<Float, DefaultToDimensionResource> {

	@Override
	public Float process(final DefaultToDimensionResource annotation, final Context context) {
		return context.getResources().getDimension(annotation.value());
	}
}