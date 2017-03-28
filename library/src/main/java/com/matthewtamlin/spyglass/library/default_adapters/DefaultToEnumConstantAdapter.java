package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.EnumUtil;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class DefaultToEnumConstantAdapter
		implements DefaultAdapter<Enum<?>, DefaultToEnumConstant> {

	@Override
	public Enum<?> getDefault(final DefaultToEnumConstant annotation, final Context context) {
		checkNotNull(annotation, "Argument \'annotation\' cannot be null.");
		checkNotNull(context, "Argument \'context\' cannot be null.");

		final int ordinal = annotation.ordinal();
		return EnumUtil.getEnumConstant(annotation.enumClass(), ordinal);
	}
}