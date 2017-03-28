package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;

public class StringHandlerAdapter implements HandlerAdapter<String, StringHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final StringHandler annotation) {

		return attrs.hasValue(annotation.attributeId());
	}

	@Override
	public String getAttributeValue(final TypedArray attrs, final StringHandler annotation) {
		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getString(annotation.attributeId());
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final StringHandler annotation) {
		return annotation.mandatory();
	}
}