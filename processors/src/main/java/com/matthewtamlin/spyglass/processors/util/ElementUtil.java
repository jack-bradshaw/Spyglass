package com.matthewtamlin.spyglass.processors.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.ElementKind.CLASS;

public class ElementUtil {
	/**
	 * Recursively searches up the element model until the enclosing class is found or the top of
	 * the model is reached. The enclosing class may be an anonymous inner class, a nested class,
	 * or a top level class. If the element has no enclosing class, null is returned.
	 *
	 * @param element
	 * 		the element to find the enclosing class of, not null
	 *
	 * @return the enclosing class as an element, null if none exists
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code element} is null
	 */
	public static TypeElement getEnclosingType(final Element element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		final Element enclosingElement = element.getEnclosingElement();

		if (enclosingElement == null) {
			// No enclosing class exists
			return null;
		} else if (enclosingElement.getKind() == CLASS) {
			// The enclosing class has been found
			return (TypeElement) enclosingElement;
		} else {
			// Keep searching up
			return getEnclosingType(enclosingElement);
		}
	}

	public static String getTypeOfNonUseParameter(final ExecutableElement e) {
		final Map<Integer, Annotation> useAnnotations = AnnotationUtil.getUseAnnotations(e);

		for (int i = 0; i < useAnnotations.size(); i++) {
			if (!useAnnotations.keySet().contains(i)) {
				return e.getParameters().get(i).asType().toString();
			}
		}

		throw new IllegalArgumentException("All parameters of argument \'e\' have use annotations.");
	}

	public static String getPackageOfType(final TypeElement e) {
		if (!e.getQualifiedName().contentEquals(".")) {
			return null;
		} else {
			final String[] splitByPeriod = e.getQualifiedName().toString().split(".");

			final StringBuilder packageBuilder = new StringBuilder();

			for (int i = 0; i < splitByPeriod.length - 1; i++) {
				packageBuilder.append(splitByPeriod[i]);
			}

			return packageBuilder.toString();
		}
	}

	public static String getSimpleNameOfType(final TypeElement e) {
		if (!e.getQualifiedName().contentEquals(".")) {
			return e.getQualifiedName().toString();
		} else {
			final String[] splitByPeriod = e.getQualifiedName().toString().split(".");
			return splitByPeriod[splitByPeriod.length - 1];
		}
	}
}