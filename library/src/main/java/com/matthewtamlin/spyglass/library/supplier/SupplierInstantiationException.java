package com.matthewtamlin.spyglass.library.supplier;

public class SupplierInstantiationException extends RuntimeException {
	public SupplierInstantiationException() {
		super();
	}

	public SupplierInstantiationException(final String message) {
		super(message);
	}

	public SupplierInstantiationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public SupplierInstantiationException(final Throwable cause) {
		super(cause);
	}
}
