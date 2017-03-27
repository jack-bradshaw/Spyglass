package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.core.EnumUtil;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumConstantHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class EnumAttributeProcessor
		implements AttributeProcessor<Enum, EnumConstantHandler> {

	@Override
	public boolean attributeValueIsAvailable(
			final TypedArray attrs,
			final EnumConstantHandler annotation) {

		checkNotNull(attrs, "Argument \'attrs\' cannot be null.");
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return attrs.hasValue(annotation.attributeId());
	}

	@Override
	public Enum getAttributeValue(final TypedArray attrs, final EnumConstantHandler annotation) {
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
	public boolean attributeIsMandatory(final EnumConstantHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}