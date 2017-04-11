package com.matthewtamlin.spyglass.library.handler_annotations;

public @interface FlagHandler {
	int attributeId();

	int flags();

	boolean mandatory() default false;
}