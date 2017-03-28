package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToColorSupplier;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToColorSupplierAdapter
		implements DefaultAdapter<Integer, DefaultToColorSupplier> {

	@Override
	public Integer getDefault(final DefaultToColorSupplier annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return instantiateSupplier(annotation.value()).get();
	}
}