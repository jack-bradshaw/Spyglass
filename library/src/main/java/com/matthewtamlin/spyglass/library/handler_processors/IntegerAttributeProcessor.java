package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.IntegerHandler;

public class IntegerAttributeProcessor implements HandlerAdapter<Integer, IntegerHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final IntegerHandler annotation) {

		// Try with different defaults and compare the results to determine if the value is present
		final int reading1 = attrs.getInt(annotation.attributeId(), 0);
		final int reading2 = attrs.getInt(annotation.attributeId(), 1);
		final boolean defaultConsistentlyReturned = (reading1 == 0) && (reading2 == 1);

		return !defaultConsistentlyReturned;
	}

	@Override
	public Integer getAttributeValue(final TypedArray attrs, final IntegerHandler annotation) {
		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getInt(annotation.attributeId(), 0);
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final IntegerHandler annotation) {
		return annotation.mandatory();
	}
}