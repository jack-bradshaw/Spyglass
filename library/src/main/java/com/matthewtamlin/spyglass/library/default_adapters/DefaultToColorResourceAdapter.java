package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorResource;

public class DefaultToColorResourceAdapter
		implements DefaultAdapter<Integer, DefaultToColorResource> {

	@Override
	public Integer getDefault(final DefaultToColorResource annotation, final Context context) {
		return ContextCompat.getColor(context, annotation.value());
	}
}