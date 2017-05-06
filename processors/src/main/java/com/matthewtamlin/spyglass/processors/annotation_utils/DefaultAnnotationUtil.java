package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.DEFAULT_ANNOTATIONS;

public class DefaultAnnotationUtil {
	public static Annotation getDefaultAnnotation(final Element method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Class<? extends Annotation> a : DEFAULT_ANNOTATIONS) {
			if (method.getAnnotation(a) != null) {
				return method.getAnnotation(a);
			}
		}

		return null;
	}

	private DefaultAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}