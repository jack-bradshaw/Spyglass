package com.matthewtamlin.spyglass.library_tests.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class MethodHelper {
	public static Method getMethodWithTag(final int tagValue, final Class clazz) {
		for (final Method m : clazz.getDeclaredMethods()) {
			final MethodTag tag = m.getAnnotation(MethodTag.class);

			if (tag != null && tag.value() == tagValue) {
				return m;
			}
		}

		// Use an error because no test should recover from this or expect this to happen
		throw new Error("No method found with tag index " + tagValue);
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface MethodTag {
		int value();
	}
}