package com.matthewtamlin.spyglass.library.core;

public class EnumUtil {
	public static <T extends Enum<?>> T getEnumConstant(final Class<T> clazz, final int ordinal) {
		final T[] constants = clazz.getEnumConstants();

		if (ordinal < 0 || ordinal > constants.length - 1) {
			final String message = "Ordinal must be between 0 and %1$s for enum class %2$s.";
			throw new IllegalArgumentException(String.format(message, constants.length - 1, clazz));
		}

		return constants[ordinal];
	}
}