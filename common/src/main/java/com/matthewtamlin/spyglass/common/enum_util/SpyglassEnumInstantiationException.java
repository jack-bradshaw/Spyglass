package com.matthewtamlin.spyglass.common.enum_util;

public class SpyglassEnumInstantiationException extends RuntimeException {
	public SpyglassEnumInstantiationException() {
		super();
	}

	public SpyglassEnumInstantiationException(final String s) {
		super(s);
	}

	public SpyglassEnumInstantiationException(final String s, final Throwable throwable) {
		super(s, throwable);
	}

	public SpyglassEnumInstantiationException(final Throwable throwable) {
		super(throwable);
	}
}