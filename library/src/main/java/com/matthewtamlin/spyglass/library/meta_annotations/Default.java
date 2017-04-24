package com.matthewtamlin.spyglass.library.meta_annotations;

import com.matthewtamlin.spyglass.library.default_adapters.DefaultAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta annotation for all default annotations. Default annotations identify methods which the
 * Spyglass framework can invoke in the absence of valid data, and they define the default values to
 * pass.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Default {
	/**
	 * @return the class of adapter which can interpret the target annotation
	 */
	Class<? extends DefaultAdapter> adapterClass();
}