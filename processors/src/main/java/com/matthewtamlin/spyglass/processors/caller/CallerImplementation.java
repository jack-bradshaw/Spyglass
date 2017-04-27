package com.matthewtamlin.spyglass.processors.caller;

import com.squareup.javapoet.TypeSpec;

public interface CallerImplementation {
	public TypeSpec toTypeSpec();
}