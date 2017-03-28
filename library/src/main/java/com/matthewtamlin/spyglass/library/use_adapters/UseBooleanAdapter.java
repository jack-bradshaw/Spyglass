package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseBoolean;

public class UseBooleanAdapter implements UseAdapter<Boolean, UseBoolean> {
	@Override
	public Boolean getValue(final UseBoolean annotation) {
		return annotation.value();
	}
}