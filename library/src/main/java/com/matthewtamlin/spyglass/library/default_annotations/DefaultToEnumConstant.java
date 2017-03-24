package com.matthewtamlin.spyglass.library.default_annotations;

public @interface DefaultToEnumConstant {
	Class<? extends Enum> enumClass();

	int ordinal();
}