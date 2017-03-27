package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.FractionHandler;

import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

public class FractionHandlerAdapter implements HandlerAdapter<Float, FractionHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final FractionHandler annotation) {

		// Try with different defaults and compare the results to determine if the value is present
		final float reading1 = attrs.getFraction(annotation.attributeId(), 1, 1, NEGATIVE_INFINITY);
		final float reading2 = attrs.getFraction(annotation.attributeId(), 1, 1, POSITIVE_INFINITY);
		final boolean defaultConsistentlyReturned =
				(reading1 == NEGATIVE_INFINITY) && (reading2 == POSITIVE_INFINITY);

		return !defaultConsistentlyReturned;
	}

	@Override
	public Float getAttributeValue(final TypedArray attrs, final FractionHandler annotation) {
		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getFraction(
					annotation.attributeId(),
					annotation.baseMultiplier(),
					annotation.parentMultiplier(),
					0);
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final FractionHandler annotation) {
		return annotation.mandatory();
	}
}