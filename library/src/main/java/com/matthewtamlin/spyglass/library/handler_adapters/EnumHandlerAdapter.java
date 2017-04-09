package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.library.handler_annotations.EnumHandler;
import com.matthewtamlin.spyglass.library.util.EnumUtil;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class EnumHandlerAdapter implements HandlerAdapter<Enum, EnumHandler> {
	@Override
	public TypedArrayAccessor<Enum> getAccessor(final EnumHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<Enum>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				// Compare two different results to see if the default is consistently returned
				final int reading1 = array.getInt(annotation.attributeId(), 0);
				final int reading2 = array.getInt(annotation.attributeId(), 1);

				return !((reading1 == 0) && (reading2 == 1));
			}

			@Override
			public Enum getValueFromArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				if (valueExistsInArray(array)) {
					final int ordinal = array.getInt(annotation.attributeId(), 0);
					return EnumUtil.getEnumConstant(annotation.enumClass(), ordinal);
				} else {
					throw new RuntimeException("No attribute found for attribute ID " +
							annotation.attributeId());
				}
			}
		};
	}

	@Override
	public int getAttributeId(final EnumHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.attributeId();
	}

	@Override
	public boolean attributeIsMandatory(final EnumHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}