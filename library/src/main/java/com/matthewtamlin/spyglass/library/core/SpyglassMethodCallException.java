package com.matthewtamlin.spyglass.library.core;

public class SpyglassMethodCallException extends RuntimeException {
	public SpyglassMethodCallException() {
		super();
	}

	public SpyglassMethodCallException(final String message) {
		super(message);
	}

	public SpyglassMethodCallException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SpyglassMethodCallException(final Throwable cause) {
		super(cause);
	}
}