package com.matthewtamlin.spyglass.processor.util;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.type.TypeMirror;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class SetUtil {
	@SafeVarargs
	public static <T> Set<T> unmodifiableSetOf(T... objects) {
		final Set<T> set = new HashSet<>();

		Collections.addAll(set, objects);

		return Collections.unmodifiableSet(set);
	}

	public static Set<String> allToString(Set<?> set) {
		checkNotNull(set, "Argument \'set\' cannot be null.");

		final Set<String> stringSet = new HashSet<>();

		for (final Object o : set) {
			stringSet.add(o.toString());
		}

		return stringSet;
	}
}