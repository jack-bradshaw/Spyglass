package com.matthewtamlin.spyglass.library.call_handler_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.SpecificEnumHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods tagged with this annotation will be called by Spyglasses to handle specific enums.
 * Tagged methods will only be called if the spyglass contains the supplied attribute ID, and the
 * attribute ID is mapped to the supplied enum ordinal.
 * <p>
 * Methods tagged with this annotation must not have default annotations, and all parameters must
 * have a use annotation.
 */
@CallHandler(adapterClass = SpecificEnumHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SpecificEnumHandler {
	/**
	 * @return the ID of the attribute handled by the tagged method
	 */
	int attributeId();

	/**
	 * @return the ordinal of the enum constant handled by the tagged method
	 */
	int ordinal();
}