package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.core.EnumUtil;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class EnumHandlerAdapter
		implements HandlerAdapter<Enum, EnumHandler> {

	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final EnumHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		// Try with different defaults and compare the results to determine if the value is present
		final int reading1 = attrs.getInt(annotation.attributeId(), 0);
		final int reading2 = attrs.getInt(annotation.attributeId(), 1);
		final boolean defaultConsistentlyReturned = (reading1 == 0) && (reading2 == 1);

		return !defaultConsistentlyReturned;
	}

	@Override
	public Enum getAttributeValue(final TypedArray attrs, final EnumHandler annotation) {
		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		if (attributeValueIsAvailable(attrs, annotation)) {
			final int ordinal = attrs.getInt(annotation.attributeId(), 0);
			return EnumUtil.getEnumConstant(annotation.enumClass(), ordinal);
		} else {
			throw new RuntimeException("No attribute found for ID " + annotation.attributeId());
		}
	}

	@Override
	public boolean attributeIsMandatory(final EnumHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}