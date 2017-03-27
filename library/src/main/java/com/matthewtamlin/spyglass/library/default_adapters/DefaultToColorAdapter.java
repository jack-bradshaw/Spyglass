package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColor;

public class DefaultToColorAdapter implements DefaultAdapter<Integer, DefaultToColor> {
	@Override
	public Integer getDefault(final DefaultToColor annotation, final Context context) {
		return annotation.value();
	}
}