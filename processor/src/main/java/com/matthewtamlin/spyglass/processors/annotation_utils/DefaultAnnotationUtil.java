package com.matthewtamlin.spyglass.processors.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.DEFAULT_ANNOTATIONS;

@Tested(testMethod = "automated")
public class DefaultAnnotationUtil {
	public static AnnotationMirror getDefaultAnnotationMirror(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : DEFAULT_ANNOTATIONS) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasDefaultAnnotation(final ExecutableElement element) {
		return getDefaultAnnotationMirror(element) != null;
	}

	private DefaultAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}