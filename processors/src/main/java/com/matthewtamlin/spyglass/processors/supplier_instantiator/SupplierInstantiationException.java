package com.matthewtamlin.spyglass.processors.supplier_instantiator;

import com.matthewtamlin.spyglass.annotations.supplier.Supplier;

/**
 * Exception to indicate that a {@link Supplier} could not be reflectively instantiated.
 */
public class SupplierInstantiationException extends RuntimeException {
	/**
	 * Constructs a new SupplierInstantiationException with no cause or message. The
	 */
	public SupplierInstantiationException() {
		super();
	}

	/**
	 * Constructs a new SupplierInstantiationException with a message but no cause.
	 */
	public SupplierInstantiationException(final String message) {
		super(message);
	}

	/**
	 * Constructs a new SupplierInstantiationException with a message and a cause.
	 */
	public SupplierInstantiationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new SupplierInstantiationException with a cause but no method.
	 */
	public SupplierInstantiationException(final Throwable cause) {
		super(cause);
	}
}