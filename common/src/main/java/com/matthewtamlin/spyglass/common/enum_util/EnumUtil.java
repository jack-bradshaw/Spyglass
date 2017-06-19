package com.matthewtamlin.spyglass.common.enum_util;

import com.matthewtamlin.java_utilities.testing.Tested;

import static com.matthewtamlin.java_utilities.checkers.IntChecker.checkGreaterThanOrEqualTo;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class EnumUtil {
	private static final String CLASS_NOT_FOUND_MESSAGE = "Could not find class \'%1$s\'.";

	private static final String CONSTANT_NOT_FOUND_MESSAGE = "Could not find constant \'%1$s\' in enum \'%2$s\'.";

	public static <T extends Enum<?>> T getEnumConstant(final Class<T> clazz, final int ordinal) {
		checkNotNull(clazz, "Argument \'clazz\' cannot be null.");

		final T[] constants = clazz.getEnumConstants();

		if (ordinal < 0 || ordinal > constants.length - 1) {
			throw new EnumInstantiationException(
					String.format(
							"Ordinal must be between 0 and %1$s for enum \'%2$s\'. Requested ordinal was: %3$s.",
							constants.length - 1,
							clazz,
							ordinal));
		}

		return constants[ordinal];
	}

	public static Enum<?> getEnumConstant(final String fullyQualifiedClassName, final int ordinal) {
		checkNotNull(fullyQualifiedClassName, "Argument \'fullyQualifiedClassName\' cannot be null.");

		final Class enumClass = getEnumClass(fullyQualifiedClassName);
		return getEnumConstant(enumClass, ordinal);
	}

	public static Enum<?> getEnumConstant(final String fullyQualifiedConstantName) {
		checkNotNull(fullyQualifiedConstantName, "Argument \'fullyQualifiedConstantName\' cannot be null.");

		final int lastDotIndex = fullyQualifiedConstantName.lastIndexOf(".");
		final String className = fullyQualifiedConstantName.substring(0, lastDotIndex);
		final String constantName = fullyQualifiedConstantName.substring(lastDotIndex + 1);

		for (final Enum<?> enumConstant : getEnumClass(className).getEnumConstants()) {
			if (enumConstant.name().equals(constantName)) {
				return enumConstant;
			}
		}

		throw new EnumInstantiationException(String.format(
				"Could not find constant \'%1$s\' in enum \'%2$s\'.",
				constantName,
				className));
	}

	@SuppressWarnings("unchecked") // Managed internally by check for null constants
	public static Class<? extends Enum<?>> getEnumClass(final String fullyQualifiedClassName) {
		checkNotNull(fullyQualifiedClassName, "Argument \'fullyQualifiedClassName\' cannot be null.");

		final Class<?> enumClass = getClassAndWrapNotFoundException(fullyQualifiedClassName);

		// Enum constants will be null if class is not an enum
		if (enumClass.getEnumConstants() == null) {
			throw new EnumInstantiationException("Class \'" + fullyQualifiedClassName + "\' is not an enum.");
		} else {
			return (Class) enumClass;
		}
	}

	private static Class<?> getClassAndWrapNotFoundException(final String fullyQualifiedClassName) {
		try {
			return Class.forName(fullyQualifiedClassName);
		} catch (final ClassNotFoundException e) {
			throw new EnumInstantiationException("Class \'" + fullyQualifiedClassName + "\' could not be found.", e);
		}
	}
}