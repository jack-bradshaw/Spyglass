package com.matthewtamlin.spyglass.library.default_adapters;

import android.content.Context;

import com.matthewtamlin.spyglass.library.core.EnumUtil;
import com.matthewtamlin.spyglass.library.core.MalformedEnumException;
import com.matthewtamlin.spyglass.library.default_annotations.DefaultToEnumConstant;

public class DefaultToEnumConstantAdapter
		implements DefaultAdapter<Enum<?>, DefaultToEnumConstant> {

	@Override
	public Enum<?> getDefault(final DefaultToEnumConstant annotation, final Context context) {
		final int ordinal = annotation.ordinal();
		return EnumUtil.getEnumConstant(annotation.enumClass(), ordinal);
	}
}