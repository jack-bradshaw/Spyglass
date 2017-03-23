package com.matthewtamlin.spyglass.library;

import static com.matthewtamlin.spyglass.library.IgnoreMode.NEVER_IGNORE;

public @interface BindColor {
	int annotationId();

	IgnoreMode ignoreMode() default NEVER_IGNORE;

	int defaultValue() default 0;
}
