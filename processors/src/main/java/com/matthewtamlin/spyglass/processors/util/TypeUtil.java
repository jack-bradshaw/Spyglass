package com.matthewtamlin.spyglass.processors.util;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class TypeUtil {
	/**
	 * Finds the enclosing class of the supplied element. The enclosing class may be an anonymous inner class, a nested
	 * class, a top level class, or a local class. If the element has no enclosing class then null is returned.
	 *
	 * @param element
	 * 		the element to find the enclosing type of, not null
	 *
	 * @return the enclosing type of the supplied element, null if none exists
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code element} is null
	 */
	public static TypeElement getEnclosingClass(final Element element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		final Element enclosingElement = element.getEnclosingElement();

		if (enclosingElement == null) {
			// The input is a top level element
			return null;

		} else if (enclosingElement.getKind() == ElementKind.CLASS) {
			// The enclosing class has been found
			return (TypeElement) enclosingElement;

		} else {
			// Keep searching up the model tree
			return getEnclosingClass(enclosingElement);
		}
	}
}