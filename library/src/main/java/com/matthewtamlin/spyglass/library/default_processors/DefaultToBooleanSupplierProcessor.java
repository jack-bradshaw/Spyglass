package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToBooleanSupplierProcessor
		implements DefaultAdapter<Boolean, DefaultToBooleanSupplier> {

	@Override
	public Boolean process(final DefaultToBooleanSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}