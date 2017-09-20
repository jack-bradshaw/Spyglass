package com.matthewtamlin.spyglass.common.enum_util;

import com.matthewtamlin.spyglass.common.exception.SpyglassRuntimeException;

/**
 * An exception to indicate that an enum could not be retrieved at runtime.
 */
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