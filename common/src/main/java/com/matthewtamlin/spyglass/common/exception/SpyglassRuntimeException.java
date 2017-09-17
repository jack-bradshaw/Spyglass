package com.matthewtamlin.spyglass.common.exception;

/**
 * A runtime exception caused by the Spyglass framework, as distinct from exceptions caused by non-Spyglass objects.
 */
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