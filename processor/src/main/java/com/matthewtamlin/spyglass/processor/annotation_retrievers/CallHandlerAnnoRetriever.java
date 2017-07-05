package com.matthewtamlin.spyglass.processor.annotation_retrievers;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processor.util.SetUtil.unmodifiableSetOf;

@Tested(testMethod = "automated")
public class CallHandlerAnnoRetriever {
	public static AnnotationMirror getAnnotation(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : getClasses()) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasAnnotation(final ExecutableElement element) {
		return getAnnotation(element) != null;
	}

	public static Set<Class<? extends Annotation>> getClasses() {
		return unmodifiableSetOf(
				SpecificEnumHandler.class,
				SpecificFlagHandler.class);
	}

	private CallHandlerAnnoRetriever() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}