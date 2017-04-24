package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDrawableResource;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Adapter for interfacing with DefaultToDrawable annotations.
 */
@Tested(testMethod = "automated")
public class DefaultToDrawableResourceAdapter
		implements DefaultAdapter<Drawable, DefaultToDrawableResource> {

	@Override
	public Drawable getDefault(final DefaultToDrawableResource annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return ContextCompat.getDrawable(context, annotation.value());
	}
}