package com.matthewtamlin.spyglass.consumer;

/**
 * Exception that indicates a required Spyglass companion could not be found at runtime.
 */
public class MissingCompanionClassException extends RuntimeException {
	public MissingCompanionClassException() {
		super();
	}

	public MissingCompanionClassException(final String message) {
		super(message);
	}

	public MissingCompanionClassException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MissingCompanionClassException(final Throwable cause) {
		super(cause);
	}
}