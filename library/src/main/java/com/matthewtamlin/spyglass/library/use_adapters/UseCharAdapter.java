package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseChar;

public class UseCharAdapter implements UseAdapter<Character, UseChar> {
	@Override
	public Character getValue(final UseChar annotation) {
		return annotation.value();
	}
}