package com.matthewtamlin.spyglass.annotations.supplier;

import com.matthewtamlin.java_utilities.testing.Tested;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

/**
 * Reflectively instantiates implementations of the {@link Supplier} interface.
 */
@Tested(testMethod = "automated")
@SuppressWarnings("TryWithIdenticalCatches") // Can't be collapsed before API 19
public class SupplierInstantiator {
	private static final String MESSAGE = "Unable to instantiate supplier of type %1$s. Is it a " +
			"concrete class with a public no-arg constructor?";

	/**
	 * Instantiates a supplier with a defined supply type.
	 *
	 * @param clazz
	 * 		the class of supplier to instantiate, not null
	 * @param <T>
	 * 		the type of object supplied by the supplier
	 *
	 * @return a new instance of the supplied class
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code clazz} is null
	 * @throws SupplierInstantiationException
	 * 		if the supplier cannot be instantiated
	 */
	public static <T> Supplier<T> instantiateSupplier(final Class<? extends Supplier<T>> clazz) {
		checkNotNull(clazz, "Argument \'clazz\' cannot be null.");

		try {
			return clazz.newInstance();
		} catch (final Exception e) {
			throw new SupplierInstantiationException(String.format(MESSAGE, clazz), e);
		}
	}

	/**
	 * Instantiates a supplier with an undefined supply type.
	 *
	 * @param clazz
	 * 		the class of supplier to instantiate, not null
	 *
	 * @return a new instance of the supplied class
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code clazz} is null
	 * @throws SupplierInstantiationException
	 * 		if the supplier cannot be instantiated
	 */
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