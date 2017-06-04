package com.matthewtamlin.spyglass.processors.grouper;

import com.matthewtamlin.java_utilities.testing.Tested;

import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class TypeElementWrapper {
	private final TypeElement element;

	public TypeElementWrapper(final TypeElement element) {
		this.element = checkNotNull(element, "Argument \'element\' cannot be null.");
	}

	public TypeElement unwrap() {
		return element;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		} else if (o == null) {
			return false;
		} else if (this.getClass() != o.getClass()) {
			return false;
		} else {
			final TypeElementWrapper castInput = (TypeElementWrapper) o;

			final String inputQualifiedName = castInput.element.getQualifiedName().toString();
			final String thisQualifiedName = this.element.getQualifiedName().toString();

			return inputQualifiedName.equals(thisQualifiedName);
		}
	}

	@Override
	public int hashCode() {
		return element.getQualifiedName().toString().hashCode();
	}
}