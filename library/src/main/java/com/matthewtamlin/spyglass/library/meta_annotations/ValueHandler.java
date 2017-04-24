package com.matthewtamlin.spyglass.library.meta_annotations;

import com.matthewtamlin.spyglass.library.value_handler_adapters.ValueHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotations marked as value handlers declare methods which the Spyglass framework can invoke
 * and pass data to.
 */

/**
 * Meta annotation for all value handler annotations. Value handler annotations identify methods
 * which the Spyglass framework can invoke and in doing so, pass data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValueHandler {
	/**
	 * @return the class of adapter which can interpret the target annotation
	 */
	Class<? extends ValueHandlerAdapter> adapterClass();
}