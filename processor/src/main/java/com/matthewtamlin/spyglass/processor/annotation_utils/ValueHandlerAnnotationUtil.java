package com.matthewtamlin.spyglass.processor.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processor.core.AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS;

@Tested(testMethod = "automated")
public class ValueHandlerAnnotationUtil {
	public static AnnotationMirror getValueHandlerAnnotationMirror(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : VALUE_HANDLER_ANNOTATIONS) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasValueHandlerAnnotation(final ExecutableElement element) {
		return getValueHandlerAnnotationMirror(element) != null;
	}

	private ValueHandlerAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}