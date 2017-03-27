package com.matthewtamlin.spyglass.library.core;

class MandatoryAttributeException extends RuntimeException {
	public MandatoryAttributeException() {
		super();
	}

	public MandatoryAttributeException(final String message) {
		super(message);
	}

	public MandatoryAttributeException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MandatoryAttributeException(final Throwable cause) {
		super(cause);
	}
}