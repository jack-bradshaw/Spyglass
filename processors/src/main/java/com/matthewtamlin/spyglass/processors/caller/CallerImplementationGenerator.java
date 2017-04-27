package com.matthewtamlin.spyglass.processors.caller;

import com.squareup.javapoet.TypeSpec;

public interface CallerImplementationGenerator {
	public TypeSpec toTypeSpec();
}