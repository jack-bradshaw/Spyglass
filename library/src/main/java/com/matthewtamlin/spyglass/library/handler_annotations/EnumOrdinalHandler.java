package com.matthewtamlin.spyglass.library.handler_annotations;

public @interface EnumOrdinalHandler {
	int attributeId();

	boolean mandatory() default false;
}