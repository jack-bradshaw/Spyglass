package com.matthewtamlin.spyglass.processor.validation;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Result {
	public abstract boolean isSuccessful();

	public abstract String getDescription();

	public static Result createSuccessful() {
		return new AutoValue_Result(true, "Validation successful.");
	}

	public static Result createFailure(final String description, final Object... args) {
		return new AutoValue_Result(false, String.format(description, args));
	}
}