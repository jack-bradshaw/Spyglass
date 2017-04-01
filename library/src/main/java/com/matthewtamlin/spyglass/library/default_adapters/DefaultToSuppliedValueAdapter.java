package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.supplier.SupplierInstantiator;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToSuppliedValue;

public class DefaultToSuppliedValueAdapter
		implements DefaultAdapter<Object, DefaultToSuppliedValue> {

	@Override
	public Object getDefault(final DefaultToSuppliedValue annotation, final Context context) {
		return SupplierInstantiator.instantiateWildcardSupplier(annotation.value()).get();
	}
}