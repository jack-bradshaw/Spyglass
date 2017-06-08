package com.matthewtamlin.spyglass.processor.validation;

/**
 * Exception to indicate that the Spyglass framework detected an illegal use of Spyglass components.
 */
public class ValidationException extends Exception {
	/**
	 * Constructs a new ValidationException with no cause or message. The
	 */
	public ValidationException() {
		super();
	}

	/**
	 * Constructs a new ValidationException with a message but no cause.
	 */
	public ValidationException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new ValidationException with a message and a cause.
	 */
	public ValidationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new ValidationException with a cause but no method.
	 */
	public ValidationException(final Throwable cause) {
		super(cause);
	}
}