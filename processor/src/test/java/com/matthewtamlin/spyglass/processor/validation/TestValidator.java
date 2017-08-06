package com.matthewtamlin.spyglass.processor.validation;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.AnnotatedElementSupplier;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TestValidator {
	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"validation/Data.java");

	private Set<Element> elements;

	private Validator validator;

	@BeforeClass
	public static void setupClass() {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
	}

	@Before
	public void setup() throws MalformedURLException {
		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		final AnnotatedElementSupplier elementSupplier = new AnnotatedElementSupplier(dataFileObject);
		elements = elementSupplier.getElementsWithAnnotation(Target.class);

		final CoreHelpers coreHelpers = new CoreHelpers(compilationRule.getElements(), compilationRule.getTypes());
		validator = new Validator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullTypeMirrorHelper() {
		new Validator(null);
	}

	@Test
	public void testValidateElement_usingDataFileElements() {
		for (final Element element : elements) {
			final Target targetAnnotation = element.getAnnotation(Target.class);
			final boolean shouldPassValidation = targetAnnotation.isValid();

			if (element.getKind() != ElementKind.METHOD) {
				throw new RuntimeException("All test elements must be executable elements (e.g. methods).");
			}

			try {
				validator.validateElement((ExecutableElement) element);

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