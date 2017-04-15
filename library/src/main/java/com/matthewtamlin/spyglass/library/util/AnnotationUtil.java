package com.matthewtamlin.spyglass.library.util;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;
import com.matthewtamlin.spyglass.library.meta_annotations.Use;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class AnnotationUtil {
	public static Annotation getValueHandlerAnnotation(final Field field) {
		checkNotNull(field, "Argument \'field \' cannot be null.");

		for (final Annotation a : field.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(ValueHandler.class)) {
				return a;
			}
		}

		return null;
	}

	public static Annotation getValueHandlerAnnotation(final Method method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Annotation a : method.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(ValueHandler.class)) {
				return a;
			}
		}

		return null;
	}

	public static Annotation getCallHandlerAnnotation(final Method method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Annotation a : method.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(CallHandler.class)) {
				return a;
			}
		}

		return null;
	}

	public static Annotation getDefaultAnnotation(final Field field) {
		checkNotNull(field, "Argument \'field \' cannot be null.");

		for (final Annotation a : field.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Default.class)) {
				return a;
			}
		}

		return null;
	}

	public static Annotation getDefaultAnnotation(final Method method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Annotation a : method.getDeclaredAnnotations()) {
			if (a.annotationType().isAnnotationPresent(Default.class)) {
				return a;
			}
		}

		return null;
	}

	public static Map<Integer, Annotation> getUseAnnotations(final Method method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

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
		checkNotNull(annotations, "Argument \'annotations \' cannot be null.");

		for (final Annotation a : annotations) {
			final Use useAnnotation = a.annotationType().getAnnotation(Use.class);

			if (useAnnotation != null) {
				return a;
			}
		}

		return null;
	}
}