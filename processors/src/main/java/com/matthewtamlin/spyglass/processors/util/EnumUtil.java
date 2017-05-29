package com.matthewtamlin.spyglass.processors.util;

import static com.matthewtamlin.java_utilities.checkers.IntChecker.checkGreaterThanOrEqualTo;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class EnumUtil {
	private static final String CLASS_NOT_FOUND_MESSAGE = "Could not find class \'%1$s\'.";

	private static final String CONSTANT_NOT_FOUND_MESSAGE = "Could not find constant \'%1$s\' in enum \'%2$s\'.";

	public static <T extends Enum<?>> T getEnumConstant(final Class<T> clazz, final int ordinal) {
		checkNotNull(clazz, "Argument \'clazz\' cannot be null.");
		checkGreaterThanOrEqualTo(ordinal, 0, "Argument \'ordinal\' cannot be less than 0.");

		final T[] constants = clazz.getEnumConstants();

		if (ordinal < 0 || ordinal > constants.length - 1) {
			throw new IllegalArgumentException(String.format(
					"Ordinal must be between 0 and %1$s for enum class %2$s.",
					constants.length - 1,
					clazz));
		}

		return constants[ordinal];
	}

	public static Enum<?> getEnumConstant(final String className, final int ordinal) throws ClassNotFoundException {
		checkNotNull(className, "Argument \'className\' cannot be null.");
		checkGreaterThanOrEqualTo(ordinal, 0, "Argument \'ordinal\' cannot be less than 0.");

		final Class enumClazz = Class.forName(className);
		return getEnumConstant(enumClazz, ordinal);
	}

	public static Enum<?> getEnumConstant(final String fullyQualifiedConstantName) throws ClassNotFoundException {
		checkNotNull(fullyQualifiedConstantName, "Argument \'fullyQualifiedConstantName\' cannot be null.");

		final int lastDotIndex = fullyQualifiedConstantName.lastIndexOf(".");
		final String className = fullyQualifiedConstantName.substring(0, lastDotIndex);
		final String constantName = fullyQualifiedConstantName.substring(lastDotIndex + 1);

		for (final Enum<?> enumConstant : getEnumClass(className).getEnumConstants()) {
			if (enumConstant.name().equals(constantName)) {
				return enumConstant;
			}
		}

		throw new RuntimeException(String.format(
				"Could not find constant \'%1$s\' in enum \'%2$s\'.",
				constantName,
				className));
	}

	@SuppressWarnings("unchecked") // Managed internally by check for null constants
	public static Class<? extends Enum<?>> getEnumClass(final String fullyQualifiedClassName)
			throws ClassNotFoundException {

		checkNotNull(fullyQualifiedClassName, "Argument \'fullyQualifiedClassName\' cannot be null.");

		final Class enumClass = (Class) Class.forName(fullyQualifiedClassName);

		// Enum constants will be null if class is not an enum
		if (enumClass.getEnumConstants() == null) {
			throw new IllegalArgumentException("Argument \'fullyQualifiedClassName\' must refer to an enum type.");
		} else {
			return enumClass;
		}
	}
}