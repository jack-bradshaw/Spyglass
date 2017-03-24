package com.matthewtamlin.spyglass.library.default_annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultToEnumConstant {
	Class<? extends Enum> enumClass();

	int ordinal();
}