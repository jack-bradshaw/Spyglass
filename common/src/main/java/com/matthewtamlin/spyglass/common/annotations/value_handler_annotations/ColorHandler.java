package com.matthewtamlin.spyglass.common.annotations.value_handler_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a color attribute. If the Spyglass framework finds
 * a color value mapped to the specified attribute, it will invoke the method and pass in the
 * value. This annotation should only be applied to methods which satisfy all of the following
 * criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one integer parameter.</li>
 * <li>Except for one integer parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface ColorHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();
}