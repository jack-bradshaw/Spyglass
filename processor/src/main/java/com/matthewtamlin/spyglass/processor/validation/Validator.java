package com.matthewtamlin.spyglass.processor.validation;

import javax.lang.model.element.ExecutableElement;

public interface Validator {
	public Result validate(final ExecutableElement element);

	public interface Result {
		public boolean isSuccess();

		public String getDescription();
	}
}