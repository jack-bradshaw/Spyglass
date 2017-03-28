package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseNull;

public class UseNullAdapter implements UseAdapter<Void, UseNull> {
	@Override
	public Void getValue(final UseNull annotation) {
		return null;
	}
}