package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;

public class DefaultToNullProcessor implements DefaultProcessor<Object, DefaultToNull> {
	@Override
	public Object process(final DefaultToNull annotation, final Context context) {
		return null;
	}
}