package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

public class DefaultToDimensionProcessor implements DefaultAdapter<Float, DefaultToDimension> {
	@Override
	public Float process(final DefaultToDimension annotation, final Context context) {
		return annotation.unit().convertToPx(context, annotation.value());
	}
}