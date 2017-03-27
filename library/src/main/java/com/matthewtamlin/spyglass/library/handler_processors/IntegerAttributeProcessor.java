package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.IntegerHandler;

public class IntegerAttributeProcessor implements AttributeProcessor<Integer, IntegerHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final IntegerHandler annotation) {

		return attrs.hasValue(annotation.attributeId());
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