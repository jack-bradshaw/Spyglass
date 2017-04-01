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
	/**
	 * Determines whether or not the supplied attributes contain a specific value. The supplied
	 * annotation contains the data needed to access the value.
	 *
	 * @param attrs
	 * 		the attributes to load data from, not null
	 * @param annotation
	 * 		defines what to load from the attributes, not null
	 *
	 * @return true if the data is available in the attribute, false otherwise
	 */
	public boolean attributeValueIsAvailable(TypedArray attrs, A annotation);

	/**
	 * Loads a specific value from the supplied attributes using the data in the supplied
	 * annotation. Calling this method without first verifying that the value is available is
	 * potentially unsafe and could cause an exception to be thrown.
	 *
	 * @param attrs
	 * 		the attributes to load data from, not null
	 * @param annotation
	 * 		defines what to load from the attributes, not null
	 *
	 * @return the loaded value, may be null
	 */
	public T getAttributeValue(TypedArray attrs, A annotation);

	/**
	 * Returns whether or not the supplied annotation has been declared as mandatory.
	 *
	 * @param annotation
	 * 		the annotation to check, not null
	 *
	 * @return true if the supplied annotation was declared mandatory, false otherwise
	 */
	public boolean attributeIsMandatory(A annotation);
}