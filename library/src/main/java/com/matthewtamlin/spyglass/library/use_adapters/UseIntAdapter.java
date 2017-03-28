package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseInt;

public class UseIntAdapter implements UseAdapter<Integer, UseInt> {
	@Override
	public Integer getValue(final UseInt annotation) {
		return annotation.value();
	}
}