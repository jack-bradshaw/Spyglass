package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;

public class DefaultToFractionResourceProcessor
		implements DefaultProcessor<Float, DefaultToFractionResource> {

	@Override
	public Float process(final DefaultToFractionResource annotation, final Context context) {
		return context.getResources().getFraction(
				annotation.resId(),
				annotation.baseMultiplier(),
				annotation.parentMultiplier());
	}
}