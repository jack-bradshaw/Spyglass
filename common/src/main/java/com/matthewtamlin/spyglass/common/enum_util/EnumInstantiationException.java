package com.matthewtamlin.spyglass.common.enum_util;

import com.matthewtamlin.spyglass.common.exception.InternalSpyglassException;

public class EnumInstantiationException extends InternalSpyglassException {
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