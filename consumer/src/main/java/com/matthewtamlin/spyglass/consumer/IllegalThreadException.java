package com.matthewtamlin.spyglass.consumer;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

/**
 * Exception to indicate that a method has been called on a illegal thread.
 */
public class IllegalThreadException extends RuntimeException {
	/**
	 * Constructs a new IllegalThreadException with no cause or message. The
	 */
	public IllegalThreadException() {
		super();
	}

	/**
	 * Constructs a new IllegalThreadException with a message but no cause.
	 */
	public IllegalThreadException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new IllegalThreadException with a message and a cause.
	 */
	public IllegalThreadException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new IllegalThreadException with a cause but no method.
	 */
	public IllegalThreadException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new IllegalThreadException with a message and a cause. This constructor
	 * allows the exception to be suppressed, and the stack trace can be made writable.
	 */
	@RequiresApi(24) // For caller
	@TargetApi(24) // For lint
	protected IllegalThreadException(
			final String message,
			final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {

		super(message, cause, enableSuppression, writableStackTrace);
	}
}