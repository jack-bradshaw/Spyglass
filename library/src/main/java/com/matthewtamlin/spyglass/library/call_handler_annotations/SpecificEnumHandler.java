package com.matthewtamlin.spyglass.library.call_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificEnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a specific enum attribute. The method will only
 * be invoked if the Spyglass framework finds the specified ordinal mapped to the specified
 * attribute. This annotation should only be applied to methods which satisfy all of the
 * following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has no default annotation.</li>
 * <li>Every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@CallHandler(adapterClass = SpecificEnumHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SpecificEnumHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	/**
	 * @return the ordinal of the handled enum constant
	 */
	int ordinal();
}