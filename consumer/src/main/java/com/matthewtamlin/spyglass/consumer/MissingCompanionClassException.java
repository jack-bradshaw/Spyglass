package com.matthewtamlin.spyglass.consumer;

/**
 * An exception to indicate that no companion class exists for a particular class.
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