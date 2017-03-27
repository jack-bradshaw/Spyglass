package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToFractionSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToFractionSupplierAdapter
		implements DefaultAdapter<Float, DefaultToFractionSupplier> {

	@Override
	public Float process(final DefaultToFractionSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}