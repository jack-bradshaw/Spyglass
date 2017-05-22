package com.matthewtamlin.spyglass.processors.grouper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkEachElementIsNotNull;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.ElementKind.CLASS;

public class Grouper {
	public static <T extends Element> Map<TypeElement, Set<T>> groupByEnclosingClass(final Set<T> elements) {
		checkNotNull(elements, "Argument \'elements\' cannot be null.");
		checkEachElementIsNotNull(elements, "Argument \'elements\' cannot contain null.");

		final Map<TypeElementWrapper, Set<T>> groupsUsingWrapper = new HashMap<>();

		TypeElementWrapper first = null;

		for (final T element : elements) {
			final TypeElementWrapper parentWrapper = new TypeElementWrapper(getEnclosingClass(element));

			if (!groupsUsingWrapper.containsKey(parentWrapper)) {
				groupsUsingWrapper.put(parentWrapper, new HashSet<T>());
			}

			groupsUsingWrapper.get(parentWrapper).add(element);
		}

		final Map<TypeElement, Set<T>> groupsWithoutWrapper = new HashMap<>();

		for (final TypeElementWrapper wrapper : groupsUsingWrapper.keySet()) {
			groupsWithoutWrapper.put(wrapper.unwrap(), groupsUsingWrapper.get(wrapper));
		}

		return groupsWithoutWrapper;
	}

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
	private static TypeElement getEnclosingClass(final Element element) {
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
			return getEnclosingClass(enclosingElement);
		}
	}

	private Grouper() {
		throw new RuntimeException("Util class. Do not instantiate.");
	}


}