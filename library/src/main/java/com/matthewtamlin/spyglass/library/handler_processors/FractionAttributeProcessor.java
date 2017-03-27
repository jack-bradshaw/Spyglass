package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.FractionHandler;

public class FractionAttributeProcessor implements AttributeProcessor<Float, FractionHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final FractionHandler annotation) {

		return attrs.hasValue(annotation.attributeId());
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