package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseString;

public class UseStringAdapter implements UseAdapter<String, UseString> {
	@Override
	public String getValue(final UseString annotation) {
		return annotation.value();
	}
}