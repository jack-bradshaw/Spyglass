package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseFloat;

public class UseFloatAdapter implements UseAdapter<Float, UseFloat> {
	@Override
	public Float getValue(final UseFloat annotation) {
		return annotation.value();
	}
}