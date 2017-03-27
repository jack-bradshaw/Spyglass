package com.matthewtamlin.spyglass.library.handler_processors;

import android.content.res.TypedArray;

import java.lang.annotation.Annotation;

/**
 * Interfaces between a Handler annotation and a set of Android view attributes.
 *
 * @param <T>
 * 		the data type the view attribute maps to
 * @param <A>
 * 		the type of annotation to interface with
 */
public interface HandlerAdapter<T, A extends Annotation> {
	public boolean attributeValueIsAvailable(TypedArray attrs, A annotation);

	public T getAttributeValue(TypedArray attrs, A annotation);

	public boolean attributeIsMandatory(A annotation);
}