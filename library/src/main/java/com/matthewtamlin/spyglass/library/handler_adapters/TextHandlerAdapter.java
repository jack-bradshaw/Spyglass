package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.TextHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class TextHandlerAdapter implements HandlerAdapter<CharSequence, TextHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final TextHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return attrs.getText(annotation.attributeId()) != null;
	}

	@Override
	public CharSequence getAttributeValue(
			final TypedArray attrs,
			final TextHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getText(annotation.attributeId());
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final TextHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}