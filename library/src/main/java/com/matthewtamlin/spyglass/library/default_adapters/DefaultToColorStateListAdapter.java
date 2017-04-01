package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorStateListResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class DefaultToColorStateListAdapter
		implements DefaultAdapter<ColorStateList, DefaultToColorStateListResource> {

	@Override
	public ColorStateList getDefault(final DefaultToColorStateListResource annotation,
			final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return ContextCompat.getColorStateList(context, annotation.value());
	}
}