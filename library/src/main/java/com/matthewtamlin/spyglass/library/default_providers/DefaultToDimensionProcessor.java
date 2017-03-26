package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

public class DefaultToDimensionProcessor implements DefaultProcessor<Float, DefaultToDimension> {
	@Override
	public Float process(final DefaultToDimension annotation, final Context context) {
		return annotation.unit().convertToPx(context, annotation.value());
	}
}