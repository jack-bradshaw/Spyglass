package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFraction;

public class DefaultToFractionProcessor implements DefaultProcessor<Float, DefaultToFraction> {
	@Override
	public Float process(final DefaultToFraction annotation, final Context context) {
		return annotation.value();
	}
}