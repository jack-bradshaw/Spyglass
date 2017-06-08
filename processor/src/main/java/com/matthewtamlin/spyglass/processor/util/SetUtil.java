package com.matthewtamlin.spyglass.processor.util;

import com.matthewtamlin.java_utilities.testing.Tested;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Tested(testMethod = "automated")
public class SetUtil {
	@SafeVarargs
	public static <T> Set<T> unmodifiableSetOf(T... objects) {
		final Set<T> set = new HashSet<>();

		Collections.addAll(set, objects);

		return Collections.unmodifiableSet(set);
	}
}