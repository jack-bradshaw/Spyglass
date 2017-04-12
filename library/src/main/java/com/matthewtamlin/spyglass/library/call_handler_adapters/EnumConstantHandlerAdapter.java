package com.matthewtamlin.spyglass.library.call_handler_adapters;

import android.content.res.TypedArray;


import com.matthewtamlin.spyglass.library.call_handler_annotations.EnumConstantHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class EnumConstantHandlerAdapter implements CallHandlerAdapter<EnumConstantHandler> {
	@Override
	public TypedArrayAccessor<Void> getAccessor(final EnumConstantHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<Void>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				// Compare two different results to see if the default is consistently returned
				final int reading1 = array.getInt(annotation.attributeId(), 0);
				final int reading2 = array.getInt(annotation.attributeId(), 1);

				final boolean consistentlyDefault = (reading1 == 0) && (reading2 == 1);

				// Even if a value is available, make sure it's has the ordinal of interest
				if (!consistentlyDefault) {
					return array.getInt(annotation.attributeId(), 0) == annotation.ordinal();
				} else {
					return false;
				}
			}

			@Override
			public Void getValueFromArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				if (valueExistsInArray(array)) {
					return null;
				} else {
					throw new RuntimeException("No attribute found for attribute ID " +
							annotation.attributeId());
				}
			}
		};
	}

	@Override
	public int getAttributeId(final EnumConstantHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.attributeId();
	}

	@Override
	public boolean isMandatory(final EnumConstantHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return false;
	}
}