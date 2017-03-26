package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

public class DefaultToDimensionProcessor implements DefaultProcessor<Integer, DefaultToDimension> {
	@Override
	public Integer process(final DefaultToDimension annotation, final Context context) {
		return annotation.unit().convertToPx(context, annotation.value());
	}
}