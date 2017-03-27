package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringResource;

public class DefaultToStringResourceAdapter
		implements DefaultAdapter<String, DefaultToStringResource> {

	@Override
	public String getDefault(final DefaultToStringResource annotation, final Context context) {
		return context.getResources().getString(annotation.value());
	}
}