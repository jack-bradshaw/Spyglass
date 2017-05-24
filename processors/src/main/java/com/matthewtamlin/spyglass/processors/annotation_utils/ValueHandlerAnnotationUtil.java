package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS;

public class ValueHandlerAnnotationUtil {
	public static Annotation getValueHandlerAnnotation(final Element element) {
		checkNotNull(element, "Argument \'element \' cannot be null.");

		for (final Class<? extends Annotation> a : VALUE_HANDLER_ANNOTATIONS) {
			if (element.getAnnotation(a) != null) {
				return element.getAnnotation(a);
			}
		}

		return null;
	}

	public static boolean hasValueAnnotation(final Element element) {
		for (final Class<? extends Annotation> a : VALUE_HANDLER_ANNOTATIONS) {
			if (element.getAnnotation(a) != null) {
				return true;
			}
		}

		return false;
	}

	private ValueHandlerAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}