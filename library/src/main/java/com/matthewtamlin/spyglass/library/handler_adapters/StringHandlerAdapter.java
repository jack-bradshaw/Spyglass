package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.StringHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class StringHandlerAdapter implements HandlerAdapter<String, StringHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final StringHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return attrs.hasValue(annotation.attributeId());
	}

	@Override
	public String getAttributeValue(final TypedArray attrs, final StringHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getString(annotation.attributeId());
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final StringHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}