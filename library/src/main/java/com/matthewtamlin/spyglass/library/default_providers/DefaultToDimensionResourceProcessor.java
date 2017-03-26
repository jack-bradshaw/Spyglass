package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionResource;

public class DefaultToDimensionResourceProcessor
		implements DefaultProcessor<Integer, DefaultToDimensionResource> {

	@Override
	public Integer process(final DefaultToDimensionResource annotation, final Context context) {
		return (int) context.getResources().getDimension(annotation.value());
	}
}