package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.matthewtamlin.spyglass.library.handler_annotations.DrawableHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class DrawableHandlerAdapter implements HandlerAdapter<Drawable, DrawableHandler> {
	@Override
	public boolean attributeValueIsAvailable(final TypedArray attrs,
			final DrawableHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return attrs.getDrawable(annotation.attributeId()) != null;
	}

	@Override
	public Drawable getAttributeValue(final TypedArray attrs, final DrawableHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getDrawable(annotation.attributeId());
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final DrawableHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}