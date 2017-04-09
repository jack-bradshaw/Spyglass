package com.matthewtamlin.spyglass.library.core;

public class MandatoryAttributeMissingException extends RuntimeException {
	public MandatoryAttributeMissingException() {
		super();
	}

	public MandatoryAttributeMissingException(final String message) {
		super(message);
	}

	public MandatoryAttributeMissingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MandatoryAttributeMissingException(final Throwable cause) {
		super(cause);
	}
}