package com.matthewtamlin.spyglass.processors.util;

import static com.matthewtamlin.java_utilities.checkers.IntChecker.checkGreaterThanOrEqualTo;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class EnumUtil {
	public static <T extends Enum<?>> T getEnumConstant(final Class<T> clazz, final int ordinal) {
		checkNotNull(clazz, "Argument \'clazz\' cannot be null.");
		checkGreaterThanOrEqualTo(ordinal, 0, "Argument \'ordinal\' cannot be less than 0.");

		final T[] constants = clazz.getEnumConstants();

		if (ordinal < 0 || ordinal > constants.length - 1) {
			final String message = "Ordinal must be between 0 and %1$s for enum class %2$s.";
			throw new IllegalArgumentException(String.format(message, constants.length - 1, clazz));
		}

		return constants[ordinal];
	}

	public static Enum<?> getEnumConstant(final String className, final int ordinal) {
		checkNotNull(className, "Argument \'className' cannot be null.");
		checkGreaterThanOrEqualTo(ordinal, 0, "Argument \'ordinal\' cannot be less than 0.");

		try {
			final Class enumClazz = Class.forName(className);
			return getEnumConstant(enumClazz, ordinal);
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Could not find class " + className, e);
		}
	}

	public static <T extends Enum<?>> T getEnumConstant(final String fullyQualifiedName) {
		checkNotNull(fullyQualifiedName, "Argument \'fullyQualifiedName\' cannot be null.");

		final String enumClassName = fullyQualifiedName.substring(0, fullyQualifiedName.lastIndexOf("."));
		final String enumConstantName = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1);

		try {
			final Class<? extends Enum<?>> enumClass = (Class) Class.forName(enumClassName);
			final Enum<?>[] enumConstants = enumClass.getEnumConstants();

			for (final Enum<?> enumConstant : enumConstants) {
				if (enumConstant.name().equals(enumConstantName)) {
					return (T) enumConstant;
				}
			}

			throw new RuntimeException(String.format(
					"Could not find constant %1$s in enum class %2$s.",
					enumConstantName,
					enumClassName));

		} catch (final ClassNotFoundException e) {
			throw new RuntimeException("Could not find class " + enumClassName, e);
		}
	}
}