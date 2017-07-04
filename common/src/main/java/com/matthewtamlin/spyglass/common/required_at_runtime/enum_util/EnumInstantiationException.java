package com.matthewtamlin.spyglass.common.required_at_runtime.enum_util;

import com.matthewtamlin.spyglass.common.required_at_runtime.exception.SpyglassRuntimeException;

public class EnumInstantiationException extends SpyglassRuntimeException {
	public EnumInstantiationException() {
		super();
	}

	public EnumInstantiationException(final String s) {
		super(s);
	}

	public EnumInstantiationException(final String s, final Throwable throwable) {
		super(s, throwable);
	}

	public EnumInstantiationException(final Throwable throwable) {
		super(throwable);
	}
}