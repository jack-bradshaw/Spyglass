package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionResource;

public class DefaultToFractionResourceAdapter
		implements DefaultAdapter<Float, DefaultToFractionResource> {

	@Override
	public Float getDefault(final DefaultToFractionResource annotation, final Context context) {
		return context.getResources().getFraction(
				annotation.resId(),
				annotation.baseMultiplier(),
				annotation.parentMultiplier());
	}
}