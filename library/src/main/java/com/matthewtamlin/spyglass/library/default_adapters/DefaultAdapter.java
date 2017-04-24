package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import java.lang.annotation.Annotation;

/**
 * Interfaces with default annotations.
 *
 * @param <T>
 * 		the type of defaults returned by the annotations this adapter interfaces with
 * @param <A>
 * 		the type of annotation this adapter interfaces with
 */
public interface DefaultAdapter<T, A extends Annotation> {
	/**
	 * Gets the default defined by the supplied annotation.
	 *
	 * @param annotation
	 * 		the annotation to evaluate, not null
	 * @param context
	 * 		a context which provides access to system resources, not null
	 *
	 * @return the default value
	 */
	public T getDefault(A annotation, Context context);
}