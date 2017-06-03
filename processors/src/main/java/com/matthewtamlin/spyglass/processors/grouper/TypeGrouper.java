package com.matthewtamlin.spyglass.processors.grouper;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkEachElementIsNotNull;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class TypeGrouper {
	public static <T extends Element> Map<TypeElementWrapper, Set<T>> groupByEnclosingType(final Set<T> elements) {
		checkNotNull(elements, "Argument \'elements\' cannot be null.");
		checkEachElementIsNotNull(elements, "Argument \'elements\' cannot contain null.");

		final Map<TypeElementWrapper, Set<T>> groups = new HashMap<>();

		for (final T element : elements) {
			final Element parent = element.getEnclosingElement();

			if (parent instanceof TypeElement) {
				final TypeElementWrapper parentWrapper = new TypeElementWrapper((TypeElement) parent);

				if (!groups.containsKey(parentWrapper)) {
					groups.put(parentWrapper, new HashSet<T>());
				}

				groups.get(parentWrapper).add(element);
			} else {
				throw new IllegalArgumentException("Argument \'elements\' contains an element which is not the " +
						"immediate child of a TypeElement.");
			}
		}

		return groups;
	}

	private TypeGrouper() {
		throw new RuntimeException("Util class. Do not instantiate.");
	}
}