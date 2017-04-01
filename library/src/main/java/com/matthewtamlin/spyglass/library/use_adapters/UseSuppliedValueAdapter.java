package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.supplier.SupplierInstantiator;
import com.matthewtamlin.spyglass.library.use_annotations.UseSuppliedValue;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class UseSuppliedValueAdapter implements UseAdapter<Object, UseSuppliedValue> {
	@Override
	public Object getValue(final UseSuppliedValue annotation) {
		checkNotNull(annotation, "Argument 'annotation' cannot be null.");

		return SupplierInstantiator.instantiateWildcardSupplier(annotation.value()).get();
	}
}