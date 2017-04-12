package com.matthewtamlin.spyglass.library.call_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class FlagHandlerAdapter implements ValueHandlerAdapter<Void, FlagHandler> {
	@Override
	public TypedArrayAccessor<Void> getAccessor(final FlagHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<Void>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				// Compare two different results to see if the default is consistently returned
				final int reading1 = array.getInt(annotation.attributeId(), 0);
				final int reading2 = array.getInt(annotation.attributeId(), 1);

				final boolean consistentlyDefault = (reading1 == 0) && (reading2 == 1);

				if (!consistentlyDefault) {
					final int handledFlags = annotation.handledFlags();
					final int foundFlags = array.getInt(annotation.attributeId(), 0);

					// Return true if at least one flag bit matches
					return (foundFlags & handledFlags) > 0;
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
	public int getAttributeId(final FlagHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.attributeId();
	}

	@Override
	public boolean isMandatory(final FlagHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return false;
	}
}