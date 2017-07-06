package com.matthewtamlin.spyglass.processor.annotation_retrievers;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.core.AnnotationRegistry;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorHelper;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class UseAnnoRetriever {
	public static AnnotationMirror getAnnotation(final VariableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.USE_ANNOS) {
			final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasAnnotation(final VariableElement element) {
		return getAnnotation(element) != null;
	}

	private UseAnnoRetriever() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}