package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanResource;

public class DefaultToBooleanResourceProcessor
		implements DefaultProcessor<Boolean, DefaultToBooleanResource> {

	@Override
	public Boolean process(final DefaultToBooleanResource annotation, final Context context) {
		return context.getResources().getBoolean(annotation.value());
	}
}