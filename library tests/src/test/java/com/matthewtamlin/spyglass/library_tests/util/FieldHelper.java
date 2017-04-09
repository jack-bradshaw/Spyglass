package com.matthewtamlin.spyglass.library_tests.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public class FieldHelper {
	public static Field getFieldWithTag(final int tagValue, final Class clazz) {
		for (final Field f : clazz.getDeclaredFields()) {
			final FieldTag tag = f.getAnnotation(FieldTag.class);

			if (tag != null && tag.value() == tagValue) {
				return f;
			}
		}

		// Use an error because no test should recover from this or expect this to happen
		throw new Error("No field found with tag index " + tagValue);
	}

	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface FieldTag {
		int value();
	}
}