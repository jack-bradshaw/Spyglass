package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;

public class DefaultToColorResourceProcessor
		implements DefaultProcessor<Integer, DefaultToColorResource> {

	@Override
	public Integer process(final DefaultToColorResource annotation, final Context context) {
		return ContextCompat.getColor(context, annotation.value());
	}
}