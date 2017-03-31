package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.ColorStateListHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class ColorStateListHandlerAdapter
		implements HandlerAdapter<ColorStateList, ColorStateListHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final ColorStateListHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return attrs.getColorStateList(annotation.attributeId()) != null;
	}

	@Override
	public ColorStateList getAttributeValue(
			final TypedArray attrs,
			final ColorStateListHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getColorStateList(annotation.attributeId());
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final ColorStateListHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}
