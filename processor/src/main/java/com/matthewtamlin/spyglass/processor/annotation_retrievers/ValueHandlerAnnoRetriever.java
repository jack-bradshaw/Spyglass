package com.matthewtamlin.spyglass.processor.annotation_retrievers;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.core.AnnotationRegistry;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorHelper;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class ValueHandlerAnnoRetriever {
	public static AnnotationMirror getAnnotation(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.VALUE_HANDLER_ANNOS) {
			final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasAnnotation(final ExecutableElement element) {
		return getAnnotation(element) != null;
	}

	private ValueHandlerAnnoRetriever() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}