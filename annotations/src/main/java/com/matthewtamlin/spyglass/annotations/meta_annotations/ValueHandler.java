package com.matthewtamlin.spyglass.annotations.meta_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta annotation for all value handler annotations. Value handler annotations identify methods
 * which the Spyglass framework can invoke and in doing so, pass data.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ValueHandler {}