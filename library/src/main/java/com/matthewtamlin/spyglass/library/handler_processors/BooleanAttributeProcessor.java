package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class BooleanAttributeProcessor implements AttributeProcessor<Boolean, BooleanHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final BooleanHandler annotation) {

		checkNotNull(attrs, "Argument 'attrs' cannot be null.");
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		final boolean reading1 = attrs.getBoolean(annotation.attributeId(), false);
		final boolean reading2 = attrs.getBoolean(annotation.attributeId(), true);
		final boolean defaultConsistentlyReturned = (reading1 != reading2);

		return !defaultConsistentlyReturned;
	}

	@Override
	public Boolean getAttributeValue(final TypedArray attrs, final BooleanHandler annotation) {
		checkNotNull(attrs, "Argument 'attrs' cannot be null.");
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getBoolean(annotation.attributeId(), false);
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final BooleanHandler annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return annotation.mandatory();
	}
}