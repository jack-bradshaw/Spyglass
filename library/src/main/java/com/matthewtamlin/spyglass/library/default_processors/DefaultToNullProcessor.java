package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;

public class DefaultToNullProcessor implements DefaultAdapter<Object, DefaultToNull> {
	@Override
	public Object process(final DefaultToNull annotation, final Context context) {
		return null;
	}
}