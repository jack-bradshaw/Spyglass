package com.matthewtamlin.spyglass.processor.core;

import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

public class CompanionNamer {
	public static String getCompanionNameFor(final TypeElement targetClass, final String companionSuffix) {
		return getPrefix(targetClass) + "_" + companionSuffix;
	}

	private static String getPrefix(final TypeElement targetClass) {
		if (targetClass.getNestingKind() == NestingKind.TOP_LEVEL) {
			return targetClass.getSimpleName().toString();

		} else {
			final TypeElement parent = (TypeElement) targetClass.getEnclosedElements();

			return getPrefix(parent) + "_" + targetClass.getSimpleName();
		}
	}
}