package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.handler_annotations.TextArrayHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class TextArrayHandlerAdapter implements HandlerAdapter<CharSequence[], TextArrayHandler> {
	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final TextArrayHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return attrs.getTextArray(annotation.attributeId()) != null;
	}

	@Override
	public CharSequence[] getAttributeValue(
			final TypedArray attrs,
			final TextArrayHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			return attrs.getTextArray(annotation.attributeId());
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final TextArrayHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}