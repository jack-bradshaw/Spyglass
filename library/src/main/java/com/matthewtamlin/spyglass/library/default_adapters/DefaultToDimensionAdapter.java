package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimension;

public class DefaultToDimensionAdapter implements DefaultAdapter<Float, DefaultToDimension> {
	@Override
	public Float getDefault(final DefaultToDimension annotation, final Context context) {
		return annotation.unit().convertToPx(context, annotation.value());
	}
}