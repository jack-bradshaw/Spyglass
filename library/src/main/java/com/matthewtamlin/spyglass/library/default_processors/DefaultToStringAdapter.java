package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToString;

public class DefaultToStringAdapter implements DefaultAdapter<String, DefaultToString> {
	@Override
	public String process(final DefaultToString annotation, final Context context) {
		return annotation.value();
	}
}