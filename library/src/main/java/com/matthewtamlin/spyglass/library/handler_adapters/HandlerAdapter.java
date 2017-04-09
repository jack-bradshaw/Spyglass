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
	 * Gets a TypedArrayAccessor which encapsulates the data retrieval logic of the supplied
	 * annotation.
	 *
	 * @param annotation
	 * 		the annotation to base the accessor on, not null
	 *
	 * @return the accessor, not null
	 */
	public TypedArrayAccessor<T> getAccessor(A annotation);

	/**
	 * @return the attribute ID declared in the supplied annotation
	 */
	public int getAttributeId(A annotation);

	/**
	 * Returns whether or not the supplied annotation has been declared as mandatory.
	 *
	 * @param annotation
	 * 		the annotation to check, not null
	 *
	 * @return true if the supplied annotation was declared mandatory, false otherwise
	 */
	public boolean attributeIsMandatory(A annotation);

	/**
	 * Accesses specific data in a TypedArray.
	 *
	 * @param <T>
	 * 		the type of the accessed data
	 */
	public interface TypedArrayAccessor<T> {
		/**
		 * Determines whether or not the supplied typed array contains the specific data of
		 * interest.
		 *
		 * @param array
		 * 		the array to check, not null
		 *
		 * @return true if the data is contained in the array, false otherwise
		 */
		public boolean valueExistsInArray(TypedArray array);

		/**
		 * Gets the specific data from the supplied typed array.
		 *
		 * @param array
		 * 		the array to access
		 *
		 * @return the data
		 */
		public T getValueFromArray(TypedArray array);
	}
}