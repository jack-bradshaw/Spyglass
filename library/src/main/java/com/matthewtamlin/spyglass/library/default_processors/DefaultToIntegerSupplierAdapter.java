package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToIntegerSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToIntegerSupplierAdapter
		implements DefaultAdapter<Integer, DefaultToIntegerSupplier> {

	@Override
	public Integer process(final DefaultToIntegerSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}