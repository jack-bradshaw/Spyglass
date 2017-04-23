package com.matthewtamlin.spyglass.library.supplier;

/**
 * Exception to indicate that a {@link Supplier} could not be reflectively instantiated.
 */
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