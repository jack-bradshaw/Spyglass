package com.matthewtamlin.spyglass.library.value_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.value_handler_adapters.DimensionHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Tags methods and fields which can be used by Spyglasses to handle dimension attributes.
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