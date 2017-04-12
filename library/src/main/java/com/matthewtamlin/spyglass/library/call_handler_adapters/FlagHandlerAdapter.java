package com.matthewtamlin.spyglass.library.call_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.call_handler_annotations.FlagHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class FlagHandlerAdapter implements CallHandlerAdapter<FlagHandler> {
	@Override
	public boolean shouldCallMethod(final FlagHandler annotation, final TypedArray attrs) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");

		if (arrayContainsValue(attrs, annotation.attributeId())) {
			final int handledFlags = annotation.handledFlags();
			final int foundFlags = attrs.getInt(annotation.attributeId(), 0);

			// Return true if at least one flag bit matches
			return (foundFlags & handledFlags) > 0;
		} else {
			return false;
		}
	}

	@Override
	public int getAttributeId(final FlagHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.attributeId();
	}

	private static boolean arrayContainsValue(final TypedArray attrs, final int attrId) {

		// Compare two different results to see if the default is consistently returned
		final int reading1 = attrs.getInt(attrId, 0);
		final int reading2 = attrs.getInt(attrId, 1);

		return !((reading1 == 0) && (reading2 == 1));
	}
}