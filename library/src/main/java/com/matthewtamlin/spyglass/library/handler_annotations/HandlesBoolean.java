package com.matthewtamlin.spyglass.library.handler_annotations;


import com.matthewtamlin.spyglass.library.meta_annotations.Handles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Handles
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
public @interface HandlesBoolean {
	int annotationId();
}