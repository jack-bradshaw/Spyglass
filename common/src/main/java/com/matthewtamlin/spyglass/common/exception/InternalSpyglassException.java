package com.matthewtamlin.spyglass.common.exception;

public class InternalSpyglassException extends RuntimeException {
	public InternalSpyglassException() {
		super();
	}

	public InternalSpyglassException(final String s) {
		super(s);
	}

	public InternalSpyglassException(final String s, final Throwable throwable) {
		super(s, throwable);
	}

	public InternalSpyglassException(final Throwable throwable) {
		super(throwable);
	}
}