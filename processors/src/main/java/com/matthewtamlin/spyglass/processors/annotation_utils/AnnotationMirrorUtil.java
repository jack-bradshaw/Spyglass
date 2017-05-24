package com.matthewtamlin.spyglass.processors.annotation_utils;

import java.lang.annotation.Annotation;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

public class AnnotationMirrorUtil {
	public static AnnotationMirror getAnnotationMirror(
			final Element element,
			final Class<? extends Annotation> annotationClass) {

		for (final AnnotationMirror mirror : element.getAnnotationMirrors()) {
			if (mirror.getAnnotationType().toString().equals(annotationClass.getName())) {
				return mirror;
			}
		}

		return null;
	}
}