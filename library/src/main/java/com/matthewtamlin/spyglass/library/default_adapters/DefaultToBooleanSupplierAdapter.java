package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.default_annotations.DefaultToBooleanSupplier;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.library.core.SupplierInstantiator.instantiateSupplier;

public class DefaultToBooleanSupplierAdapter
		implements DefaultAdapter<Boolean, DefaultToBooleanSupplier> {

	@Override
	public Boolean getDefault(final DefaultToBooleanSupplier annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");

		return instantiateSupplier(annotation.value()).get();
	}
}