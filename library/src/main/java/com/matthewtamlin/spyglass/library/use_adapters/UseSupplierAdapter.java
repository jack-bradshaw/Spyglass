package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.core.SupplierInstantiator;
import com.matthewtamlin.spyglass.library.use_annotations.UseSupplier;

public class UseSupplierAdapter implements UseAdapter<Object, UseSupplier> {
	@Override
	public Object getValue(final UseSupplier annotation) {
		return SupplierInstantiator.instantiateSupplier(annotation.value()).get();
	}
}