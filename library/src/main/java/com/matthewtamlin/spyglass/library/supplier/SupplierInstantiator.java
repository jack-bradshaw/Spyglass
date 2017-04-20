package com.matthewtamlin.spyglass.library.supplier;

import com.matthewtamlin.java_utilities.testing.Tested;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
@SuppressWarnings("TryWithIdenticalCatches") // Can't be collapsed before API 19
public class SupplierInstantiator {
	private static final String MESSAGE = "Unable to instantiate supplier of type %1$s. Is it a " +
			"concrete class with a public no-arg constructor?";

	public static <T> Supplier<T> instantiateSupplier(final Class<? extends Supplier<T>> clazz) {
		checkNotNull(clazz, "Argument \'clazz\' cannot be null.");

		try {
			return clazz.newInstance();
		} catch (final Exception e) {
			throw new SupplierInstantiationException(String.format(MESSAGE, clazz), e);
		}
	}

	public static Supplier<?> instantiateWildcardSupplier(
			final Class<? extends Supplier<?>> clazz) {

		checkNotNull(clazz, "Argument \'clazz\' cannot be null.");
		
		try {
			return clazz.newInstance();
		} catch (final Exception e) {
			throw new SupplierInstantiationException(String.format(MESSAGE, clazz), e);
		}
	}
}