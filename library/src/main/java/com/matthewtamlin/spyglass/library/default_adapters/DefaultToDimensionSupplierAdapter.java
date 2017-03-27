package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToDimensionSupplierAdapter
		implements DefaultAdapter<Float, DefaultToDimensionSupplier> {

	@Override
	public Float getDefault(final DefaultToDimensionSupplier annotation, final Context context) {
		final float rawValue = instantiateSupplier(annotation.valueSupplier()).get();
		final DimensionUnit unit = instantiateSupplier(annotation.unitSupplier()).get();

		return unit.convertToPx(context, rawValue);
	}
}