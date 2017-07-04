package com.matthewtamlin.spyglass.common.exception;

public class SpyglassRuntimeException extends RuntimeException {
	public SpyglassRuntimeException() {
		super();
	}

	public SpyglassRuntimeException(final String s) {
		super(s);
	}

	public SpyglassRuntimeException(final String s, final Throwable throwable) {
		super(s, throwable);
	}

	public SpyglassRuntimeException(final Throwable throwable) {
		super(throwable);
	}
}