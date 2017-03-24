package com.matthewtamlin.spyglass.library.default_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
public @interface DefaultToIntegerResource {
	int value();
}