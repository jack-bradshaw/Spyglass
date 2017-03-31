package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.FloatHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

public class FloatHandlerAdapter implements HandlerAdapter<Float, FloatHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final FloatHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		// Try with different defaults and compare the results to determine if the value is present
		final float reading1 = attrs.getFloat(annotation.attributeId(), NEGATIVE_INFINITY);
		final float reading2 = attrs.getFloat(annotation.attributeId(), POSITIVE_INFINITY);
		final boolean defaultConsistentlyReturned =
				(reading1 == NEGATIVE_INFINITY) && (reading2 == POSITIVE_INFINITY);

		return !defaultConsistentlyReturned;
	}

	@Override
	public Float getAttributeValue(
			final TypedArray attrs,
			final FloatHandler annotation) {

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getFloat(annotation.attributeId(), 0);
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final FloatHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}