package com.matthewtamlin.spyglass.integration_tests;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ReceivedValue<T> {
	private boolean exists;

	private T value;

	public static <T> ReceivedValue<T> of(final T value) {
		final ReceivedValue<T> receivedValue = new ReceivedValue<>();

		receivedValue.exists = true;
		receivedValue.value = value;

		return receivedValue;
	}

	public static <T> ReceivedValue<T> none() {
		final ReceivedValue<T> receivedValue = new ReceivedValue<>();

		receivedValue.exists = false;
		receivedValue.value = null;

		return receivedValue;
	}

	public boolean exists() {
		return exists;
	}

	public T get() {
		if (!exists) {
			throw new RuntimeException("No received value.");
		}

		return value;
	}

	@Override
	public int hashCode() {
		final HashCodeBuilder builder = new HashCodeBuilder(83, 13);

		builder.append(exists);
		builder.append(value);

		return builder.toHashCode();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;

		} else if (obj == this) {
			return true;

		} else if (obj.getClass() != ReceivedValue.class) {
			return false;

		} else {
			final ReceivedValue objCast = (ReceivedValue) obj;

			final EqualsBuilder builder = new EqualsBuilder();

			builder.append(exists, objCast.exists);
			builder.append(value, objCast.value);

			return builder.isEquals();
		}
	}

	@Override
	public String toString() {
		if (exists()) {
			return value == null ? "Returned value of: null" : "Returned value of: " + value;
		} else {
			return "No returned value.";
		}
	}
}