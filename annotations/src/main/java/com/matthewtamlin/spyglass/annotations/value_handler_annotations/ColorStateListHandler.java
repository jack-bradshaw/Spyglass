package com.matthewtamlin.spyglass.annotations.value_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.ValueHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a color state list attribute. If the Spyglass framework
 * finds a color state list value mapped to the specified attribute, it will invoke the method and
 * pass in the value. This annotation should only be applied to methods which satisfy all of the
 * following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one integer array parameter.</li>
 * <li>Except for one integer array parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@ValueHandler
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ColorStateListHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();
}