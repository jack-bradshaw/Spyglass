package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseDouble;

public class UseDoubleAdapter implements UseAdapter<Double, UseDouble> {
	@Override
	public Double getValue(final UseDouble annotation) {
		return annotation.value();
	}
}