package com.matthewtamlin.spyglass.library.util;

import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;
import com.matthewtamlin.spyglass.library.meta_annotations.Use;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtil {
	public static Annotation getHandlerAnnotation(final Field field) {
		for (final Annotation a : field.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Handler.class)) {
				return a;
			}
		}

		return null;
	}

	public static Annotation getHandlerAnnotation(final Method method) {
		for (final Annotation a : method.getDeclaredAnnotations()) {
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

	public static Annotation getDefaultAnnotation(final Method method) {
		for (final Annotation a : method.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Default.class)) {
				return a;
			}
		}

		return null;
	}

	public static void validateAnnotations(final Field field) throws SpyglassValidationException {
		//TODO
	}

	public static void validateAnnotations(final Method method) throws SpyglassValidationException {
		//TODO
	}

	public static Map<Integer, Annotation> getUseAnnotations(final Method method) {
		final Map<Integer, Annotation> useAnnotationsByIndex = new HashMap<>();

		final Annotation[][] annotationsByParam = method.getParameterAnnotations();

		for (int i = 0; i < annotationsByParam.length; i++) {
			final Annotation[] singleParamAnnotations = annotationsByParam[i];
			final Annotation useAnnotation = getUseAnnotationForMethodParam(singleParamAnnotations);

			if (useAnnotation != null) {
				useAnnotationsByIndex.put(i, useAnnotation);
			}
		}

		return useAnnotationsByIndex;
	}

	private static Annotation getUseAnnotationForMethodParam(final Annotation[] annotations) {
		for (final Annotation a : annotations) {
			final Use useAnnotation = a.annotationType().getAnnotation(Use.class);

			if (useAnnotation != null) {
				return a;
			}
		}

		return null;
	}
}