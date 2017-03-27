package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;

public class DefaultToStringResourceProcesor
		implements DefaultAdapter<String, DefaultToStringResource> {

	@Override
	public String process(final DefaultToStringResource annotation, final Context context) {
		return context.getResources().getString(annotation.value());
	}
}