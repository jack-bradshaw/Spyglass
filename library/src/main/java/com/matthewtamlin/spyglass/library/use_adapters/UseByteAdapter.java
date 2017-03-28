package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseByte;

public class UseByteAdapter implements UseAdapter<Byte, UseByte> {
	@Override
	public Byte getValue(final UseByte annotation) {
		return annotation.value();
	}
}