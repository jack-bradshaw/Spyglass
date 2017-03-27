package com.matthewtamlin.spyglass.library.default_processors;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToColorSupplierProcessor
		implements DefaultAdapter<Integer, DefaultToColorSupplier> {

	@Override
	public Integer process(final DefaultToColorSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}