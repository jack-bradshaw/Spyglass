package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColor;

public class DefaultToColorProcessor implements DefaultProcessor<Integer, DefaultToColor> {
	@Override
	public Integer process(final DefaultToColor annotation, final Context context) {
		return annotation.value();
	}
}