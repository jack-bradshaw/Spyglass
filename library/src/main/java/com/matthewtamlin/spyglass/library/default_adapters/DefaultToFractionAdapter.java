package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFraction;

public class DefaultToFractionAdapter implements DefaultAdapter<Float, DefaultToFraction> {
	@Override
	public Float getDefault(final DefaultToFraction annotation, final Context context) {
		return annotation.value();
	}
}