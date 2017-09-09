package com.matthewtamlin.spyglass.processor.core;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;

public class CompanionNamer {
	public static String getCompanionNameFor(final TypeElement targetClass) {
		return getParentChain(targetClass) + "_SpyglassCompanion";
	}

	private static String getParentChain(final TypeElement targetClass) {
		// if input is top level class return it
		// otherwise return the parent chain plus it

		if (targetClass.getNestingKind() == NestingKind.TOP_LEVEL) {
			return targetClass.getSimpleName().toString();
		} else {
			final Element parent = targetClass.getEnclosingElement();

			if (parent.getKind() != ElementKind.CLASS) {
				throw new RuntimeException("Cannot create parent chain. Non-class parent found.");
			}

			return (getParentChain((TypeElement) parent)) + "_" + targetClass.getSimpleName().toString();
		}
	}
}