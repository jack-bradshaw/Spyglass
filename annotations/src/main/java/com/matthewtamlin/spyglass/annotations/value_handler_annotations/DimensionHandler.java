package com.matthewtamlin.spyglass.annotations.value_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.value_handler_adapters.DimensionHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling a dimension attribute. If the Spyglass framework finds
 * a dimension value mapped to the specified attribute, it will invoke the method and pass in the
 * value. If the dimension resource is not defined in pixels, it will be converted to pixels
 * before being passed into the method. This annotation should only be applied to methods which
 * satisfy all of the following criteria:
 * <ul>
 * <li>The method has no other handler annotations.</li>
 * <li>The method has at least one float parameter.</li>
 * <li>Except for one float parameter, every parameter has a use annotation.</li>
 * </ul>
 */
@Tested(testMethod = "automated")
@ValueHandler(adapterClass = DimensionHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DimensionHandler {
	/**
	 * @return the resource ID of the handled attribute
	 */
	int attributeId();
}