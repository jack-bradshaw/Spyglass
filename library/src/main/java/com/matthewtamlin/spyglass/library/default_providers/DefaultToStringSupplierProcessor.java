package com.matthewtamlin.spyglass.library.default_providers;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToStringSupplier;

import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToStringSupplierProcessor
		implements DefaultProcessor<String, DefaultToStringSupplier> {

	@Override
	public String process(final DefaultToStringSupplier annotation, final Context context) {
		return instantiateSupplier(annotation.value()).get();
	}
}