package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToColorSupplierAdapter
		implements DefaultAdapter<Integer, DefaultToColorSupplier> {

	@Override
	public Integer getDefault(final DefaultToColorSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}