package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseShort;

public class UseShortAdapter implements UseAdapter<Short, UseShort> {
	@Override
	public Short getValue(final UseShort annotation) {
		return annotation.value();
	}
}