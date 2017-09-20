package com.matthewtamlin.spyglass.annotations.annotations.value_handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling an integer attribute. If the Spyglass framework finds an integer value
 * mapped to the attribute ID, it will invoke the method and pass in the value. This annotation should only be
 * applied to methods which satisfy all of the following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one {@code Number} parameter.</li>
 * <li>Except for one {@code Number} parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface IntegerHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();
}