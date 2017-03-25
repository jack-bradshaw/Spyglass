package com.matthewtamlin.spyglass.library.core;

public class IllegalThreadException extends RuntimeException {
	public IllegalThreadException() {
		super();
	}

	public IllegalThreadException(final String message) {
		super(message);
	}

	public IllegalThreadException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public IllegalThreadException(final Throwable cause) {
		super(cause);
	}

	protected IllegalThreadException(final String message, final Throwable cause,
			final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
