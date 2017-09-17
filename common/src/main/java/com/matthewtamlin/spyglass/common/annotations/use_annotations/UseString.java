package com.matthewtamlin.spyglass.common.annotations.use_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines the value to supply to the annotated parameter when the Spyglass framework invokes the
 * method. This annotation should only be applied to parameters which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The parameter is part of a method which has a handler annotation.</li>
 * <li>The parameter has no other use annotations.</li>
 * <li>The parameter accepts a String value.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.PARAMETER)
public @interface UseString {
	/**
	 * @return the value to pass
	 */
	String value();
}