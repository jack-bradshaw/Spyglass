package com.matthewtamlin.spyglass.processors.functional;

/**
 * Accepts an object and returns something.
 *
 * @param <A> the type of object to accept
 * @param <R> the type of object to return
 */
public interface ParametrisedSupplier<A, R> {
	public R supplyFor(A object);
}