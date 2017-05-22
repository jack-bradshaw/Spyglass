package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.DEFAULT_ANNOTATIONS;

public class DefaultAnnotationUtil {
	public static Annotation getDefaultAnnotation(final Element element) {
		checkNotNull(element, "Argument \'element \' cannot be null.");

		for (final Class<? extends Annotation> a : DEFAULT_ANNOTATIONS) {
			if (element.getAnnotation(a) != null) {
				return element.getAnnotation(a);
			}
		}

		return null;
	}

	public static boolean isDefaultAnnotation(final Annotation anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		return DEFAULT_ANNOTATIONS.contains(anno.annotationType());
	}

	public static boolean hasDefaultAnnotation(final Element element) {
		checkNotNull(element, "Argument \'element \' cannot be null.");

		for (final Class<? extends Annotation> a : DEFAULT_ANNOTATIONS) {
			if (element.getAnnotation(a) != null) {
				return true;
			}
		}

		return false;
	}

	private DefaultAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}