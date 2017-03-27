package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.MalformedEnumException;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;

public class DefaultToEnumConstantAdapter
		implements DefaultAdapter<Enum<?>, DefaultToEnumConstant> {

	@Override
	public Enum<?> process(final DefaultToEnumConstant annotation, final Context context) {
		final int ordinal = annotation.ordinal();
		final Enum[] constants = annotation.enumClass().getEnumConstants();

		if (ordinal < 0 || ordinal > constants.length - 1) {
			final String message = "Found ordinal value %1$s for enum %2$s. Ordinal must be in " +
					"the range 0 to %3$s.";

			throw new MalformedEnumException(
					String.format(
							message,
							ordinal,
							annotation.enumClass(),
							constants.length - 1));
		}

		return constants[ordinal];
	}
}