package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;

public class DefaultToStringResourceProcesor
		implements DefaultProcessor<String, DefaultToStringResource> {

	@Override
	public String process(final DefaultToStringResource annotation, final Context context) {
		return context.getResources().getString(annotation.value());
	}
}