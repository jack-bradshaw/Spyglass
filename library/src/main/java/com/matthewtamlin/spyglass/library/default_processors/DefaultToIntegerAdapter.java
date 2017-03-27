package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;

public class DefaultToIntegerAdapter
		implements DefaultAdapter<Integer, DefaultToInteger> {

	@Override
	public Integer process(final DefaultToInteger annotation, final Context context) {
		return annotation.value();
	}
}