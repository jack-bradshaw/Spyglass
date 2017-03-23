package com.matthewtamlin.spyglass.library;

public @interface BindEnumConstant {
	int annotationId();

	Class<? extends Enum> enumClass();

	int ordinal();
}