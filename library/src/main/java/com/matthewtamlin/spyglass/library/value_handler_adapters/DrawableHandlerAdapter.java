package com.matthewtamlin.spyglass.library.value_handler_adapters;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.value_handler_annotations.DrawableHandler;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class DrawableHandlerAdapter implements ValueHandlerAdapter<Drawable, DrawableHandler> {
	@Override
	public TypedArrayAccessor<Drawable> getAccessor(final DrawableHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return new TypedArrayAccessor<Drawable>() {
			@Override
			public boolean valueExistsInArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				return array.getDrawable(annotation.attributeId()) != null;
			}

			@Override
			public Drawable getValueFromArray(final TypedArray array) {
				checkNotNull(array, "Argument \'array\' cannot be null.");

				if (valueExistsInArray(array)) {
					return array.getDrawable(annotation.attributeId());
				} else {
					throw new RuntimeException("No attribute found for attribute ID " +
							annotation.attributeId());
				}
			}
		};
	}

	@Override
	public int getAttributeId(final DrawableHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.attributeId();
	}

	@Override
	public boolean isMandatory(final DrawableHandler annotation) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return annotation.mandatory();
	}
}