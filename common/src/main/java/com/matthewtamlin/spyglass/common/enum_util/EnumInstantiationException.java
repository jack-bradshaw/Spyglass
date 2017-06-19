package com.matthewtamlin.spyglass.common.enum_util;

public class EnumInstantiationException extends RuntimeException {
	public EnumInstantiationException() {
		super();
	}

	public EnumInstantiationException(final String s) {
		super(s);
	}

	public EnumInstantiationException(final String s, final Throwable throwable) {
		super(s, throwable);
	}

	public EnumInstantiationException(final Throwable throwable) {
		super(throwable);
	}
}