package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.core.supplier.SupplierInstantiator;
import com.matthewtamlin.spyglass.library.use_annotations.UseSupplier;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseSupplierAdapter implements UseAdapter<Object, UseSupplier> {
	@Override
	public Object getValue(final UseSupplier annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return SupplierInstantiator.instantiateWildcardSupplier(annotation.value()).get();
	}
}