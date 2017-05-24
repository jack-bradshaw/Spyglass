package com.matthewtamlin.spyglass.processors.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.USE_ANNOTATIONS;

@Tested(testMethod = "automated")
public class UseAnnotationUtil {
	public static AnnotationMirror getUseAnnotationMirror(final VariableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : USE_ANNOTATIONS) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasUseAnnotation(final VariableElement element) {
		return getUseAnnotationMirror(element) != null;
	}

	private UseAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}