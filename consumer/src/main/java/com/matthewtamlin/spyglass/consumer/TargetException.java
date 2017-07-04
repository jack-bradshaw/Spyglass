package com.matthewtamlin.spyglass.consumer;

public class TargetException extends RuntimeException {
	public TargetException() {
		super();
	}

	public TargetException(final String message) {
		super(message);
	}

	public TargetException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public TargetException(final Throwable cause) {
		super(cause);
	}
}