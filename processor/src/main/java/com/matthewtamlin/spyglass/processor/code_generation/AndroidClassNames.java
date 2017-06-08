package com.matthewtamlin.spyglass.processor.code_generation;

import com.squareup.javapoet.ClassName;

public class AndroidClassNames {
	public static final ClassName CONTEXT = ClassName.get("android.content", "Context");

	public static final ClassName TYPED_ARRAY = ClassName.get("android.content.res", "TypedArray");

	public static final ClassName CONTEXT_COMPAT = ClassName.get("android.support.v4.content", "ContextCompat");

	public static final ClassName DISPLAY_METRICS = ClassName.get("android.util", "DisplayMetrics");

	public static final ClassName TYPED_VALUE = ClassName.get("android.util", "TypedValue");

	private AndroidClassNames() {
		throw new RuntimeException("Constants class. Do not instantiate.");
	}
}