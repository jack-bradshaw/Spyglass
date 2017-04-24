package com.matthewtamlin.spyglass.library.core;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

/**
 * Exception to indicate that an attempt was made to instantiate an object via a builder with an
 * invalid state. The exact nature of the invalid state is defined by the builder.
 */
public class InvalidBuilderStateException extends RuntimeException {
	/**
	 * Constructs a new InvalidBuilderStateException with no cause or message. The
	 */
	public InvalidBuilderStateException() {
		super();
	}

	/**
	 * Constructs a new InvalidBuilderStateException with a message but no cause.
	 */
	public InvalidBuilderStateException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new InvalidBuilderStateException with a message and a cause.
	 */
	public InvalidBuilderStateException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new InvalidBuilderStateException with a cause but no method.
	 */
	public InvalidBuilderStateException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new InvalidBuilderStateException with a message and a cause. This constructor
	 * allows the exception to be suppressed, and the stack trace can be made writable.
	 */
	@RequiresApi(24) // For caller
	@TargetApi(24) // For lint
	protected InvalidBuilderStateException(
			final String message,
			final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {

		super(message, cause, enableSuppression, writableStackTrace);
	}
}