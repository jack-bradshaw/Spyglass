package com.matthewtamlin.spyglass.processors.functional;

/**
 * Accepts an object and returns something.
 *
 * @param <R> the type of object to return
 * @param <A> the type of object to accept
 */
public interface Consumer<R, A> {
	public R accept(A object);
}