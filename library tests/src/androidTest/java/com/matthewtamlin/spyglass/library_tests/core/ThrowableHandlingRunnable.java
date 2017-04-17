package com.matthewtamlin.spyglass.library_tests.core;

public abstract class ThrowableHandlingRunnable<T extends Throwable> implements Runnable {
	private T throwable;

	public void setThrowable(final T throwable) {
		this.throwable = throwable;
	}

	public T getThrowable() {
		return throwable;
	}
}