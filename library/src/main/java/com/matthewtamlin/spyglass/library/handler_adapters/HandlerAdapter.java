package com.matthewtamlin.spyglass.library.handler_adapters;

import android.content.res.TypedArray;

import java.lang.annotation.Annotation;

/**
 * Provides information about handler annotations.
 *
 * @param <T>
 * 		the type of attribute data this adapter provides access to
 * @param <A>
 * 		the type of annotation accepted by this adapter
 */
public interface HandlerAdapter<T, A extends Annotation> {
	/**
	 * Returns whether or not the supplied annotation has been declared as mandatory.
	 *
	 * @param annotation
	 * 		the annotation to check, not null
	 *
	 * @return true if the supplied annotation was declared mandatory, false otherwise
	 */
	public boolean attributeIsMandatory(A annotation);

	public interface TypedArrayAccessor<T> {
		public boolean valueExistsInArray(TypedArray array);

		public T getValueFromArray(TypedArray array);
	}
}