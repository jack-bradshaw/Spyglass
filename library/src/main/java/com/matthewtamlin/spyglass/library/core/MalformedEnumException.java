package com.matthewtamlin.spyglass.library.core;

public class MalformedEnumException extends RuntimeException {
	public MalformedEnumException() {
		super();
	}

	public MalformedEnumException(final String message) {
		super(message);
	}

	public MalformedEnumException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public MalformedEnumException(final Throwable cause) {
		super(cause);
	}
}
