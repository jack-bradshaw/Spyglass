package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.USE_ANNOTATIONS;

public class UseAnnotationUtil {
	public static AnnotationMirror getUseAnnotationMirror(final VariableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> useAnnotationClass : USE_ANNOTATIONS) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, useAnnotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
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