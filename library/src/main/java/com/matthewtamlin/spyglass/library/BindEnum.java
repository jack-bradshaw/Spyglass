package com.matthewtamlin.spyglass.library;

public @interface BindEnum {
	int annotationId();

	Class<? extends Enum> enumClass();

	boolean ignoreIfAttributeMissing() default false;

	int defaultOrdinal() default 0;
}