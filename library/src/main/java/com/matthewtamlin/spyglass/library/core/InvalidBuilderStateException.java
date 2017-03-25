package com.matthewtamlin.spyglass.library.core;

public class InvalidBuilderStateException extends RuntimeException {
	public InvalidBuilderStateException() {
		super();
	}

	public InvalidBuilderStateException(final String message) {
		super(message);
	}

	public InvalidBuilderStateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidBuilderStateException(final Throwable cause) {
		super(cause);
	}
}