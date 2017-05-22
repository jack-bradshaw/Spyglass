package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.CALL_HANDLER_ANNOTATIONS;

public class CallHandlerAnnotationUtil {
	public static Annotation getCallHandlerAnnotation(final Element method) {
		checkNotNull(method, "Argument \'method \' cannot be null.");

		for (final Class<? extends Annotation> a : CALL_HANDLER_ANNOTATIONS) {
			if (method.getAnnotation(a) != null) {
				return method.getAnnotation(a);
			}
		}

		return null;
	}

	public static boolean isCallHandlerAnnotation(final Annotation anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		return CALL_HANDLER_ANNOTATIONS.contains(anno.annotationType());
	}

	public static boolean hasCallHandlerAnnotation(final Element element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> a : CALL_HANDLER_ANNOTATIONS) {
			if (element.getAnnotation(a) != null) {
				return true;
			}
		}

		return false;
	}

	private CallHandlerAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}