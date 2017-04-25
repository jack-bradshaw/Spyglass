package com.matthewtamlin.spyglass.annotations.meta_annotations;

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
public @interface CallHandler {}