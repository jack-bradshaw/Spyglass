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
			final String message = "Ordinal must be between 0 and %1$s for enum class %2$s.";
			throw new IllegalArgumentException(String.format(message, constants.length - 1, clazz));
		}

		return constants[ordinal];
	}

	public static Enum<?> getEnumConstant(final String className, final int ordinal) throws ClassNotFoundException {
		checkNotNull(className, "Argument \'className' cannot be null.");
		checkGreaterThanOrEqualTo(ordinal, 0, "Argument \'ordinal\' cannot be less than 0.");

		final Class enumClazz = Class.forName(className);
		return getEnumConstant(enumClazz, ordinal);
	}

	public static Enum<?> getEnumConstant(final String fullyQualifiedName) throws ClassNotFoundException {
		checkNotNull(fullyQualifiedName, "Argument \'fullyQualifiedName\' cannot be null.");

		final String enumClassName = fullyQualifiedName.substring(0, fullyQualifiedName.lastIndexOf("."));
		final String enumConstantName = fullyQualifiedName.substring(fullyQualifiedName.lastIndexOf(".") + 1);

		final Class<? extends Enum<?>> enumClass = getEnumClass(enumClassName);
		final Enum<?>[] enumConstants = enumClass.getEnumConstants();

		for (final Enum<?> enumConstant : enumConstants) {
			if (enumConstant.name().equals(enumConstantName)) {
				return enumConstant;
			}
		}

		throw new RuntimeException(String.format(
				"Could not find constant \'%1$s\' in enum \'%2$s\'.",
				enumConstantName,
				enumClassName));
	}

	@SuppressWarnings("unchecked") // Managed internally by null constants check
	public static Class<? extends Enum<?>> getEnumClass(final String fullyQualifiedClassName)
			throws ClassNotFoundException {

		final Class enumClass = (Class) Class.forName(fullyQualifiedClassName);

		// Enum constants will be null if class is not an enum
		if (enumClass.getEnumConstants() == null) {
			throw new IllegalArgumentException("Argument \'fullyQualifiedClassName\' must refer to an enum type.");
		} else {
			return enumClass;
		}
	}
}