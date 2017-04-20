package com.matthewtamlin.spyglass.library.supplier;

@SuppressWarnings("TryWithIdenticalCatches") // Can't be collapsed before API 19
public class SupplierInstantiator {
	private static final String MESSAGE = "Unable to instantiate supplier of type %1$s. Is it a " +
			"concrete class with a public no-arg constructor?";

	public static <T> Supplier<T> instantiateSupplier(final Class<? extends Supplier<T>> clazz) {
		try {
			return clazz.newInstance();
		} catch (final Exception e) {
			throw new SupplierInstantiationException(String.format(MESSAGE, clazz), e);
		}
	}

	public static Supplier<?> instantiateWildcardSupplier(
			final Class<? extends Supplier<?>> clazz) {

		try {
			return clazz.newInstance();
		} catch (final Exception e) {
			throw new SupplierInstantiationException(String.format(MESSAGE, clazz), e);
		}
	}
}