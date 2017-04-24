package com.matthewtamlin.spyglass.library.meta_annotations;

import com.matthewtamlin.spyglass.library.call_handler_adapters.CallHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta annotation for all call handler annotations. Call handler annotations identify methods
 * which the Spyglass framework can invoke without passing any data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CallHandler {
	/**
	 * @return the class of adapter which can interpret the target annotation
	 */
	Class<? extends CallHandlerAdapter> adapterClass();
}