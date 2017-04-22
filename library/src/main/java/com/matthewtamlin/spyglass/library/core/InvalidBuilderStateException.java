package com.matthewtamlin.spyglass.library.core;

/**
 * Exception to indicate that an attempt was made to instantiate an object via a builder with an
 * invalid state. The exact nature of the invalid state is defined by the builder.
 */
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