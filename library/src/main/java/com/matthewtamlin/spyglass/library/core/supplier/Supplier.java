package com.matthewtamlin.spyglass.library.core.supplier;

/**
 * Supplies something.
 *
 * @param <T>
 * 		the type of the supplied object
 */
public interface Supplier<T> {
	/**
	 * Supplied something. The same object does not need to be supplied each time.
	 *
	 * @return the object
	 */
	public T get();
}