package com.matthewtamlin.spyglass.common.required_at_runtime.enum_util;

import com.matthewtamlin.java_utilities.testing.Tested;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class EnumUtil {
	public static <T extends Enum<?>> T getEnumConstant(final Class<T> clazz, final int ordinal)
			throws EnumInstantiationException {

		checkNotNull(clazz, "Argument \'clazz\' cannot be null.");

		final T[] constants = clazz.getEnumConstants();

		if (ordinal < 0) {
			throw new EnumInstantiationException(String.format(
					"Ordinal must be greater than 0 for enum \'%1$s\'. Found %2$s.",
					clazz,
					ordinal));
		}

		if (ordinal > constants.length - 1) {
			throw new EnumInstantiationException(String.format(
					"Ordinal must be less than %1$s for enum \'%2$s\'. Found %3$s.",
					constants.length,
					clazz,
					ordinal));
		}

		return constants[ordinal];
	}

	public static Enum<?> getEnumConstant(final String fullyQualifiedClassName, final int ordinal)
			throws EnumInstantiationException {

		checkNotNull(fullyQualifiedClassName, "Argument \'fullyQualifiedClassName\' cannot be null.");

		return getEnumConstant(getEnumClass(fullyQualifiedClassName), ordinal);
	}

	public static Enum<?> getEnumConstant(final String fullyQualifiedConstantName) throws EnumInstantiationException {
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
	public static Class<? extends Enum<?>> getEnumClass(final String fullyQualifiedClassName)
			throws EnumInstantiationException {

		checkNotNull(fullyQualifiedClassName, "Argument \'fullyQualifiedClassName\' cannot be null.");

		final Class<?> enumClass = getClassAndWrapNotFoundException(fullyQualifiedClassName);

		if (enumClass.getEnumConstants() == null) {
			throw new EnumInstantiationException("Class \'%" + fullyQualifiedClassName + "\' is not an enum.");
		}

		return (Class) enumClass;
	}

	private static Class<?> getClassAndWrapNotFoundException(final String fullyQualifiedClassName)
			throws EnumInstantiationException {

		try {
			return Class.forName(fullyQualifiedClassName);
		} catch (final ClassNotFoundException e) {
			throw new EnumInstantiationException("Class \'" + fullyQualifiedClassName + "\' could not be found.", e);
		}
	}
}