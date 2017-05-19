package com.matthewtamlin.spyglass.processors.validation;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.AnnotatedElementSupplier;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TestValidator {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors/" +
			"validation/Data.java");

	private Set<Element> elements;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		final AnnotatedElementSupplier elementSupplier = new AnnotatedElementSupplier(dataFileObject);

		elements = elementSupplier.getElementsWithAnnotation(Target.class);
	}

	@Test
	public void testValidateElement_usingDataFileElements() throws CompilerMissingException {
		for (final Element element : elements) {
			final Target targetAnnotation = element.getAnnotation(Target.class);
			final boolean shouldPassValidation = targetAnnotation.isValid();

			try {
				Validator.validateElement(element);

				if (!shouldPassValidation) {
					throw new RuntimeException(
							String.format(
									"Element %1$s should have failed validation but passed.",
									element.getSimpleName()));
				}
			} catch (final ValidationException exception) {
				if (shouldPassValidation) {
					throw new RuntimeException(
							String.format(
									"Element %1$s should have passed validation but failed with error message: %2$s",
									element.getSimpleName(),
									exception.getMessage())
					);
				}
			}
		}
	}
}