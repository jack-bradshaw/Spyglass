package com.matthewtamlin.spyglass.library.value_handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.value_handler_annotations.BooleanHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class BooleanHandlerAdapter implements ValueHandlerAdapter<Boolean, BooleanHandler> {
	@Override
	public TypedArrayAccessor<Boolean> getAccessor(final BooleanHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<Boolean>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				// Compare two different results to see if the default is consistently returned
				final boolean reading1 = array.getBoolean(annotation.attributeId(), false);
				final boolean reading2 = array.getBoolean(annotation.attributeId(), true);

				return !((reading1 == false) && (reading2 == true));
			}

			@Override
			public Boolean getValueFromArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				if (valueExistsInArray(array)) {
					return array.getBoolean(annotation.attributeId(), false);
				} else {
					throw new RuntimeException("No attribute found for attribute ID "
							+ annotation.attributeId());
				}
			}
		};
	}

	@Override
	public int getAttributeId(final BooleanHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.attributeId();
	}

	@Override
	public boolean isMandatory(final BooleanHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}