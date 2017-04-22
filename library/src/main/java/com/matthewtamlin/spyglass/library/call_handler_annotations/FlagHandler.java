package com.matthewtamlin.spyglass.library.call_handler_annotations;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.call_handler_adapters.FlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Declares a method capable of handling flag attributes. Methods tagged with this annotation must
 * not have default annotations, and all parameters must have use annotations.
 */
@Tested(testMethod = "automated")
@CallHandler(adapterClass = FlagHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FlagHandler {
	/**
	 * @return the ID of the attribute handled by the tagged method
	 */
	int attributeId();

	/**
	 * @return the flags handled by the tagged method
	 */
	int handledFlags();
}