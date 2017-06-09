package com.matthewtamlin.spyglass.consumer;

public class InvalidSpyglassCompanionException extends RuntimeException {
	public InvalidSpyglassCompanionException() {
		super();
	}

	public InvalidSpyglassCompanionException(final String message) {
		super(message);
	}

	public InvalidSpyglassCompanionException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidSpyglassCompanionException(final Throwable cause) {
		super(cause);
	}
}