package com.matthewtamlin.spyglass.library.use_adapters;

import java.lang.annotation.Annotation;

/**
 * Interfaces with a use annotations.
 *
 * @param <T>
 * 		the type of the accessed values
 * @param <A>
 * 		the type of annotation this adapter interfaces with
 */
public interface UseAdapter<T, A extends Annotation> {
	/**
	 * Gets the value defined by the supplied annotation.
	 *
	 * @param annotation
	 * 		the annotation to evaluate, not null
	 *
	 * @return the value
	 */
	public T getValue(A annotation);
}