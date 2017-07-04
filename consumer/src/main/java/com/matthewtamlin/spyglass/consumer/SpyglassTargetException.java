package com.matthewtamlin.spyglass.consumer;

public class SpyglassTargetException extends RuntimeException {
	public SpyglassTargetException() {
		super();
	}

	public SpyglassTargetException(final String message) {
		super(message);
	}

	public SpyglassTargetException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SpyglassTargetException(final Throwable cause) {
		super(cause);
	}
}