package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import java.lang.annotation.Annotation;

/**
 * Interfaces with a handler annotation to provide information about the annotation itself, and
 * give access to attribute values defined by the annotation.
 *
 * @param <T>
 * 		the type of attribute data this adapter provides access to
 * @param <A>
 * 		the type of annotation accepted by this adapter
 */
public interface HandlerAdapter<T, A extends Annotation> {
	public boolean attributeValueIsAvailable(TypedArray attrs, A annotation);

	public T getAttributeValue(TypedArray attrs, A annotation);

	public boolean attributeIsMandatory(A annotation);
}