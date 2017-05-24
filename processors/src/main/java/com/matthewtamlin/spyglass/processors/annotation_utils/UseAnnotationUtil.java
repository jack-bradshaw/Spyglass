package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;

import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.USE_ANNOTATIONS;

public class UseAnnotationUtil {
	public static boolean isUseAnnotation(final Annotation anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		return USE_ANNOTATIONS.contains(anno.annotationType());
	}

	public static boolean hasUseAnnotation(final VariableElement element) {
		for (final Class<? extends Annotation> a : USE_ANNOTATIONS) {
			if (element.getAnnotation(a) != null) {
				return true;
			}
		}

		return false;
	}

	private UseAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}