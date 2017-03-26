package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.Supplier;
import com.matthewtamlin.spyglass.library.core.SupplierInstantiator;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorSupplier;

public class DefaultToColorSupplierProcessor
		implements DefaultProcessor<Integer, DefaultToColorSupplier> {
	@Override
	public Integer process(final DefaultToColorSupplier annotation, final Context context) {
		Supplier<Integer> supplier = SupplierInstantiator.instantiateSupplier(annotation.value());

		return supplier.get();
	}
}