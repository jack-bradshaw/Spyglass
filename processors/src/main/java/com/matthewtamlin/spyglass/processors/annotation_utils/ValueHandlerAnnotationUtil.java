package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.DEFAULT_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS;

public class ValueHandlerAnnotationUtil {
	public static boolean hasValueAnnotation(final ExecutableElement element) {
		return getValueHandlerAnnotation(element) != null;
	}

	private ValueHandlerAnnotationUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}