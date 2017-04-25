package com.matthewtamlin.spyglass.annotations.supplier;

/**
 * Supplies something.
 *
 * @param <T>
 * 		the type of the supplied object
 */
public interface Supplier<T> {
	/**
	 * Supplies something. The same object does not need to be supplied each time.
	 *
	 * @return the supplied object, may be null
	 */
	public T get();
}