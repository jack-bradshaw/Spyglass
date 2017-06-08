package com.matthewtamlin.spyglass.processor.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class AnnotationMirrorUtil {
	public static AnnotationMirror getAnnotationMirror(
			final Element element,
			final Class<? extends Annotation> annotationClass) {

		checkNotNull(element, "Argument \'element\' cannot be null.");
		checkNotNull(annotationClass, "Argument \'annotationClass\' cannot be null.");

		for (final AnnotationMirror mirror : element.getAnnotationMirrors()) {
			if (mirror.getAnnotationType().toString().equals(annotationClass.getName())) {
				return mirror;
			}
		}

		return null;
	}

	public static AnnotationValue getAnnotationValueIgnoringDefaults(
			final AnnotationMirror mirror,
			final String valueKey) {

		checkNotNull(mirror, "Argument \'mirror\' cannot be null.");
		checkNotNull(valueKey, "Argument \'valueKey\' cannot be null.");

		final Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();

		for (final ExecutableElement mapKey : values.keySet()) {
			if (mapKey.getSimpleName().toString().equals(valueKey)) {
				return values.get(mapKey);
			}
		}

		return null;
	}

	public static AnnotationValue getAnnotationValueWithDefaults(
			final AnnotationMirror mirror,
			final String valueKey,
			final Elements elementUtil) {

		checkNotNull(mirror, "Argument \'mirror\' cannot be null.");
		checkNotNull(valueKey, "Argument \'valueKey\' cannot be null.");
		checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");

		final Map<? extends ExecutableElement, ? extends AnnotationValue> values =
				elementUtil.getElementValuesWithDefaults(mirror);

		for (final ExecutableElement mapKey : values.keySet()) {
			if (mapKey.getSimpleName().toString().equals(valueKey)) {
				return values.get(mapKey);
			}
		}

		return null;
	}
}