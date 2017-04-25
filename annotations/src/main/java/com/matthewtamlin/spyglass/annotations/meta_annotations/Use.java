package com.matthewtamlin.spyglass.annotations.meta_annotations;

import com.matthewtamlin.spyglass.library.use_adapters.UseAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta annotation for all use annotations. Use annotations define the values to pass to non-data
 * method parameters when invoked by the Spyglass framework.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Use {
	/**
	 * @return the class of adapter which can interpret the target annotation
	 */
	Class<? extends UseAdapter> adapterClass();
}