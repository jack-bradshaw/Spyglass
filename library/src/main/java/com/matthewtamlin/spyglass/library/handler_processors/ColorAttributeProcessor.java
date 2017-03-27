package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.ColorHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class ColorAttributeProcessor implements AttributeProcessor<Integer, ColorHandler> {
	@Override
	public boolean attributeValueIsAvailable(final TypedArray attrs,
			final ColorHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		final int reading1 = attrs.getColor(annotation.attributeId(), 0);
		final int reading2 = attrs.getColor(annotation.attributeId(), 1);
		final boolean defaultConsistentlyReturned = (reading1 != reading2);

		return !defaultConsistentlyReturned;
	}

	@Override
	public Integer getAttributeValue(final TypedArray attrs, final ColorHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return attrs.getColor(annotation.attributeId(), 0);
	}

	@Override
	public boolean attributeIsMandatory(final ColorHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}