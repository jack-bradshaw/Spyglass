package com.matthewtamlin.spyglass.library.util;

/**
 * Exception to indicate that the Spyglass framework found a method with an invalid combination 
 * of handler annotations, default annotations and/or use annotation.
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