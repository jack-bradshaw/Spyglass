package com.matthewtamlin.spyglass.annotations.call_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.CallHandler;
import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificFlagHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling specific flag attributes. The method will only
 * be invoked if the Spyglass framework finds at least one of the specified flags mapped to the
 * specified attribute. This annotation should only be applied to methods which satisfy all of the
 * following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has no default annotation.</li>
 * <li>Every parameter has a use annotation.</li>
 * </ul>
 * Is it valid for a method with this annotation to have no parameters.
 */
@Tested(testMethod = "automated")
@CallHandler(adapterClass = SpecificFlagHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SpecificFlagHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();

	/**
	 * @return the specific flags handled by the method
	 */
	int handledFlags();
}