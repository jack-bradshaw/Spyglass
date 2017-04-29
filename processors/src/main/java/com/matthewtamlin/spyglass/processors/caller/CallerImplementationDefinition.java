package com.matthewtamlin.spyglass.processors.caller;

import com.squareup.javapoet.TypeSpec;

public interface CallerImplementationDefinition {
	public TypeSpec toTypeSpec();
}