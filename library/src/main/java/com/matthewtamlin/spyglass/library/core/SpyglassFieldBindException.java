package com.matthewtamlin.spyglass.library.core;

public class SpyglassFieldBindException extends RuntimeException {
	public SpyglassFieldBindException() {
		super();
	}

	public SpyglassFieldBindException(final String message) {
		super(message);
	}

	public SpyglassFieldBindException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SpyglassFieldBindException(final Throwable cause) {
		super(cause);
	}
}