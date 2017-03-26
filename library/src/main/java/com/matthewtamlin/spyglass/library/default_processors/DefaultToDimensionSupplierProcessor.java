package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToDimensionSupplierProcessor
		implements DefaultProcessor<Float, DefaultToDimensionSupplier> {

	@Override
	public Float process(final DefaultToDimensionSupplier annotation, final Context context) {
		final float rawValue = instantiateSupplier(annotation.valueSupplier()).get();
		final DimensionUnit unit = instantiateSupplier(annotation.unitSupplier()).get();

		return unit.convertToPx(context, rawValue);
	}
}