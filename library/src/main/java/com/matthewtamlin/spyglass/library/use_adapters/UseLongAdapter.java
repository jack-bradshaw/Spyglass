package com.matthewtamlin.spyglass.library.use_adapters;

import com.matthewtamlin.spyglass.library.use_annotations.UseLong;

public class UseLongAdapter implements UseAdapter<Long, UseLong> {
	@Override
	public Long getValue(final UseLong annotation) {
		return annotation.value();
	}
}