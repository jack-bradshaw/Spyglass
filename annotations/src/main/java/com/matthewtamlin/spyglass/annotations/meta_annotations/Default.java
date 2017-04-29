package com.matthewtamlin.spyglass.annotations.meta_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Meta annotation for all default annotations. Default annotations identify methods which the
 * Spyglass framework can invoke in the absence of valid data, and they define the default values to
 * pass.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Default {}