package com.matthewtamlin.spyglass.consumer;

public class SpyglassInvocationException extends RuntimeException {
	public SpyglassInvocationException() {
		super();
	}

	public SpyglassInvocationException(final String message) {
		super(message);
	}

	public SpyglassInvocationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SpyglassInvocationException(final Throwable cause) {
		super(cause);
	}
}