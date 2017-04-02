package com.matthewtamlin.spyglass.library.core;

import com.matthewtamlin.spyglass.library.meta_annotations.Use;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class ArgumentUtil {
	public static Map<Integer, Object> getArgsFromUseAnnotations(final Method method) {
		//TODO
		return null;
	}

	public static Integer getIndexOfUnannotatedArg(final Method method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		// Iterate over the each method parameter
		for (Annotation[] annotationsOnParam : method.getParameterAnnotations()) {
			// Iterate over each annotation on a single parameter
			for (Annotation annotation : annotationsOnParam) {
				if (annotation.annotationType().isAnnotationPresent(Use.class)) {
					// The parameter has a Use annotation, so move to the next parameter
					break;
				}
			}
		}

		// All parameters are annotated with a Use annotation
		return null;
	}

	public static Integer countArgs(final Method method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		return method.getParameterTypes().length;
	}
}