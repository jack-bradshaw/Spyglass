package com.matthewtamlin.spyglass.consumer;

/**
 * Generates companion class names based on target classes.
 */
public class CompanionNamer {
	public static String getCompanionNameFor(final Class<?> targetClass) {
		return getParentChain(targetClass) + "_SpyglassCompanion";
	}

	private static String getParentChain(final Class<?> targetClass) {
		final Class<?> enclosingClass = targetClass.getEnclosingClass();

		return enclosingClass == null ?
				targetClass.getSimpleName() :
				getParentChain(targetClass.getEnclosingClass()) + "_" + targetClass.getSimpleName();
	}

	private CompanionNamer() {
		throw new RuntimeException("Static utility class. Do not instantiate.");
	}
}