package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToTextArrayResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class DefaultToTextArrayResourceAdapter
		implements DefaultAdapter<CharSequence, DefaultToTextArrayResource> {

	@Override
	public CharSequence getDefault(final DefaultToTextArrayResource annotation,
			final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return context.getResources().getText(annotation.value());
	}
}