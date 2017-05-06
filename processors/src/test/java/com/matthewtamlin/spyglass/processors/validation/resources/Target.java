package com.matthewtamlin.spyglass.processors.validation.resources;

public @interface Target {
	boolean isValid();

	String failureMessage();
}
