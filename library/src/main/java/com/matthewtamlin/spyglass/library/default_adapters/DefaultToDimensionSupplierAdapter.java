package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.DimensionUnit;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToDimensionSupplier;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.library.core.supplier.SupplierInstantiator.instantiateSupplier;

public class DefaultToDimensionSupplierAdapter
		implements DefaultAdapter<Float, DefaultToDimensionSupplier> {

	@Override
	public Float getDefault(final DefaultToDimensionSupplier annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		final float rawValue = instantiateSupplier(annotation.valueSupplier()).get();
		final DimensionUnit unit = instantiateSupplier(annotation.unitSupplier()).get();

		return unit.convertToPx(context, rawValue);
	}
}