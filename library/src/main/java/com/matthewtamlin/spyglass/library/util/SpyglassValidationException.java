package com.matthewtamlin.spyglass.library.util;

public class SpyglassValidationException extends RuntimeException {
	public SpyglassValidationException() {
		super();
	}

	public SpyglassValidationException(final String message) {
		super(message);
	}

	public SpyglassValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SpyglassValidationException(final Throwable cause) {
		super(cause);
	}
}