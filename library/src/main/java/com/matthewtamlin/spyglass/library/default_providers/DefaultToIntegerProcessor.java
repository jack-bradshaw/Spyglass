package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToInteger;

public class DefaultToIntegerProcessor
		implements DefaultProcessor<Integer, DefaultToInteger> {

	@Override
	public Integer process(final DefaultToInteger annotation, final Context context) {
		return annotation.value();
	}
}