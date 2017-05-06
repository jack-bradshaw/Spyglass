package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.USE_ANNOTATIONS;

public class UseAnnotationUtil {
	public static Map<Integer, Annotation> getUseAnnotations(final ExecutableElement method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		final Map<Integer, Annotation> useAnnotationsByIndex = new HashMap<>();
		final List<? extends VariableElement> params = method.getParameters();

		for (int i = 0; i < params.size(); i++) {
			for (final Class<? extends Annotation> a : USE_ANNOTATIONS) {
				final Annotation foundAnnotation = params.get(i).getAnnotation(a);

				if (foundAnnotation != null) {
					useAnnotationsByIndex.put(i, foundAnnotation);
				}
			}
		}

		return useAnnotationsByIndex;
	}

	private UseAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}