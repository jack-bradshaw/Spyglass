package com.matthewtamlin.spyglass.processors.util;

/**
 * Exception to indicate that the Spyglass framework detected a method with an illegal
 * combination of annotations.
 */
public class SpyglassValidationException extends RuntimeException {
	/**
	 * Constructs a new SpyglassValidationException with no cause or message. The
	 */
	public SpyglassValidationException() {
		super();
	}

	/**
	 * Constructs a new SpyglassValidationException with a message but no cause.
	 */
	public SpyglassValidationException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new SpyglassValidationException with a message and a cause.
	 */
	public SpyglassValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new SpyglassValidationException with a cause but no method.
	 */
	public SpyglassValidationException(final Throwable cause) {
		super(cause);
	}
}