package com.matthewtamlin.spyglass.library.core;

import android.annotation.TargetApi;
import android.support.annotation.RequiresApi;

/**
 * Exception to indicate that the Spyglass framework was unable to reflectively invoke a method.
 */
public class SpyglassMethodCallException extends RuntimeException {
	/**
	 * Constructs a new SpyglassMethodCallException with no cause or message. The
	 */
	public SpyglassMethodCallException() {
		super();
	}

	/**
	 * Constructs a new SpyglassMethodCallException with a message but no cause.
	 */
	public SpyglassMethodCallException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new SpyglassMethodCallException with a message and a cause.
	 */
	public SpyglassMethodCallException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new SpyglassMethodCallException with a cause but no method.
	 */
	public SpyglassMethodCallException(final Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new SpyglassMethodCallException with a message and a cause. This constructor
	 * allows the exception to be suppressed, and the stack trace can be made writable.
	 */
	@RequiresApi(24) // For caller
	@TargetApi(24) // For lint
	protected SpyglassMethodCallException(
			final String message,
			final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {

		super(message, cause, enableSuppression, writableStackTrace);
	}
}