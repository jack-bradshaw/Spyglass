package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.meta_annotations.Default;

import java.lang.annotation.Annotation;

/**
 * Interfaces with an annotation marked with {@link Default} to get the default value it specifies.
 *
 * @param <T>
 * 		the type of default returned by this adapter
 * @param <A>
 * 		the type of annotation this adapter can interface with
 */
public interface DefaultAdapter<T, A extends Annotation> {
	public T getDefault(A annotation, Context context);
}