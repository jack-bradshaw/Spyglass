package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;

public class DefaultToStringAdapter implements DefaultAdapter<String, DefaultToString> {
	@Override
	public String getDefault(final DefaultToString annotation, final Context context) {
		return annotation.value();
	}
}