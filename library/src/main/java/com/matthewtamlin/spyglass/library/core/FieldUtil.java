package com.matthewtamlin.spyglass.library.core;

import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class FieldUtil {
	public static Annotation getHandlerAnnotation(final Field field) {
		for (final Annotation a : field.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Handler.class)) {
				return a;
			}
		}

		return null;
	}

	public static Annotation getDefaultAnnotation(final Field field) {
		for (final Annotation a : field.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Default.class)) {
				return a;
			}
		}

		return null;
	}

	public static void assignValueToField(
			final Object fieldParent,
			final Field field,
			final Object value)
			throws SpyglassFieldBindException {

		checkNotNull(field, "Argument 'field' cannot be null.");

		try {
			field.setAccessible(true);
			field.set(fieldParent, value);

		} catch (final SecurityException | IllegalAccessException e) {
			throw new SpyglassFieldBindException("Unable to access field " + field.getName(), e);

		} catch (final IllegalArgumentException e) {
			throw new SpyglassFieldBindException(
					String.format(
							"Unable to assign a value of type %1$s to a field of type %2$s.",
							value == null ? "\'null\'" : value.getClass().getName(),
							field.getType().getName()),
					e);
		}
	}
}