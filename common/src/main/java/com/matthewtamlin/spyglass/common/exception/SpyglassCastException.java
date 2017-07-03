package com.matthewtamlin.spyglass.common.exception;

public class SpyglassCastException extends InternalSpyglassException {
	public SpyglassCastException() {
		super();
	}

	public SpyglassCastException(final String s) {
		super(s);
	}

	public SpyglassCastException(final String s, final Throwable throwable) {
		super(s, throwable);
	}

	public SpyglassCastException(final Throwable throwable) {
		super(throwable);
	}
}