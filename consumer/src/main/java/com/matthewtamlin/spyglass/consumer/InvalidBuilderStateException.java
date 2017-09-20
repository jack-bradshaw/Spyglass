package com.matthewtamlin.spyglass.consumer;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

/**
 * Exception that indicates an attempt was made to instantiate an object via a builder with an invalid state.
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