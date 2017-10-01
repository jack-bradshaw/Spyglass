package com.matthewtamlin.spyglass.processor.definitions;

import com.squareup.javapoet.ClassName;

/**
 * Definitions for classes available in Android projects.
 */
public class AndroidClassNames {
	public static final ClassName CONTEXT = ClassName.get("android.content", "Context");

	public static final ClassName TYPED_ARRAY = ClassName.get("android.content.res", "TypedArray");

	public static final ClassName CONTEXT_COMPAT = ClassName.get("android.support.v4.content", "ContextCompat");

	public static final ClassName DISPLAY_METRICS = ClassName.get("android.util", "DisplayMetrics");

	public static final ClassName TYPED_VALUE = ClassName.get("android.util", "TypedValue");

	public static final ClassName COLOR_STATE_LIST = ClassName.get("android.content.res", "ColorStateList");

	public static final ClassName DRAWABLE = ClassName.get("android.graphics.drawable", "Drawable");

	public static final ClassName ATTRIBUTE_SET = ClassName.get("android.util", "AttributeSet");

	private AndroidClassNames() {
		throw new RuntimeException("Constants class. Do not instantiate.");
	}
}