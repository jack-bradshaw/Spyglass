package com.matthewtamlin.spyglass.library.call_handler_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.FlagHandlerAdapter;
import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods tagged with this annotation will be called by Spyglasses to handle flag attributes.
 * Tagged methods will only be called if the spyglass contains the supplied attribute ID, and the
 * attribute ID is mapped to at least one of the supplied flags.
 * <p>
 * Methods tagged with this annotation must not have default annotations, and all parameters must
 * have a use annotation.
 */
@CallHandler(adapterClass = FlagHandlerAdapter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface FlagHandler {
	/**
	 * @return the attribute handled by the tagged method
	 */
	int attributeId();

	/**
	 * @return the flags handled by the tagged method
	 */
	int handledFlags();
}