package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.supplier.SupplierInstantiator;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToSuppliedValue;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class DefaultToSuppliedValueAdapter
		implements DefaultAdapter<Object, DefaultToSuppliedValue> {

	@Override
	public Object getDefault(final DefaultToSuppliedValue annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		return SupplierInstantiator.instantiateWildcardSupplier(annotation.value()).get();
	}
}