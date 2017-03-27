package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.DimensionHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

public class DimensionAttributeProcessor implements AttributeProcessor<Float, DimensionHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final DimensionHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		// Try with different defaults and compare the results to determine if the value is present
		final float reading1 = attrs.getDimension(annotation.attributeId(), NEGATIVE_INFINITY);
		final float reading2 = attrs.getDimension(annotation.attributeId(), POSITIVE_INFINITY);

		final boolean defaultConsistentlyReturned =
				(reading1 == NEGATIVE_INFINITY) && (reading2 == POSITIVE_INFINITY);

		return !defaultConsistentlyReturned;
	}

	@Override
	public Float getAttributeValue(final TypedArray attrs, final DimensionHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getDimension(annotation.attributeId(), 0f);
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final DimensionHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}