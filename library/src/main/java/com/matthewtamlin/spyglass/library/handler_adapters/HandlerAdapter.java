package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import java.lang.annotation.Annotation;

/**
 * Accepts a set of attributes and a handler annotation specifying values to load from the
 * attributes.
 *
 * @param <T>
 * 		the type of data loaded by this adapter
 * @param <A>
 * 		the type of annotation to load
 */
public interface HandlerAdapter<T, A extends Annotation> {
	public boolean attributeValueIsAvailable(TypedArray attrs, A annotation);

	public T getAttributeValue(TypedArray attrs, A annotation);

	public boolean attributeIsMandatory(A annotation);
}