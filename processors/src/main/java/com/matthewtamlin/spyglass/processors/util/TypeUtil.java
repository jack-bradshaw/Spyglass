package com.matthewtamlin.spyglass.processors.util;

import javax.lang.model.element.TypeElement;

public class TypeUtil {
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