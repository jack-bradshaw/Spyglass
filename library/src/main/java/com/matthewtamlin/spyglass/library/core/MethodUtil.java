package com.matthewtamlin.spyglass.library.core;

import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class MethodUtil {
	public static Annotation getHandlerAnnotation(final Method method) {
		for (final Annotation a : method.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Handler.class)) {
				return a;
			}
		}

		return null;
	}

	public static Annotation getDefaultAnnotation(final Method method) {
		for (final Annotation a : method.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Default.class)) {
				return a;
			}
		}

		return null;
	}
}