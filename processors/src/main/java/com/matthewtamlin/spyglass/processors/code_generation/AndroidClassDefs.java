package com.matthewtamlin.spyglass.processors.code_generation;

import com.squareup.javapoet.ClassName;

public class AndroidClassDefs {
	public static final ClassName CONTEXT = ClassName.get("android.content", "Context");

	public static final ClassName TYPED_ARRAY = ClassName.get("android.content.res", "TypedArray");
}