package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.core.EnumUtil;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumConstantHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class EnumConstantHandlerAdapter implements HandlerAdapter<Void, EnumConstantHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final EnumConstantHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		// Try with different defaults and compare the results to determine if the value is present
		final int reading1 = attrs.getInt(annotation.attributeId(), 0);
		final int reading2 = attrs.getInt(annotation.attributeId(), 1);
		final boolean defaultConsistentlyReturned = (reading1 == 0) && (reading2 == 1);

		// If there is an enum available, make sure it's has the ordinal of interest
		if (!defaultConsistentlyReturned) {
			return attrs.getInt(annotation.attributeId(), 0) == annotation.ordinal();
		} else {
			return false;
		}
	}

	@Override
	public Void getAttributeValue(final TypedArray attrs, final EnumConstantHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return null;
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final EnumConstantHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return false;
	}
}