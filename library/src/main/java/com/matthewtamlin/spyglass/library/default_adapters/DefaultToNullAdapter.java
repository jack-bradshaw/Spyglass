package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToNull;

public class DefaultToNullAdapter implements DefaultAdapter<Object, DefaultToNull> {
	@Override
	public Object process(final DefaultToNull annotation, final Context context) {
		return null;
	}
}