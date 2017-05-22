package com.matthewtamlin.spyglass.processors.code_generation.invocation_literal_generator;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.java_compiler_utilities.element_supplier.CompilerMissingException;
import com.matthewtamlin.java_compiler_utilities.element_supplier.IdBasedElementSupplier;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static com.matthewtamlin.spyglass.processors.code_generation.InvocationLiteralGenerator.buildInvocationLiteralFor;
import static com.matthewtamlin.spyglass.processors.code_generation.invocation_literal_generator.Data.PLACEHOLDER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(JUnit4.class)
public class TestInvocationLiteralGenerator {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors/" +
			"code_generation/invocation_literal_generator/Data.java");

	private Set<Element> withPlaceholderElements;

	private Set<Element> withoutPlaceholderElements;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());
		final IdBasedElementSupplier elementSupplier = new IdBasedElementSupplier(dataFileObject);

		withPlaceholderElements = elementSupplier.getElementsWithId(Data.ELEMENT_ID_WITH_PLACEHOLDER);
		withoutPlaceholderElements = elementSupplier.getElementsWithId(Data.ELEMENT_ID_WITHOUT_PLACEHOLDER);
	}

	@Test
	public void testValidateElement_withPlaceholder() throws CompilerMissingException {
		for (final Element element : withPlaceholderElements) {
			final ExpectedResult expectedResultAnnotation = element.getAnnotation(ExpectedResult.class);

			final String actualResult = buildInvocationLiteralFor((ExecutableElement) element, PLACEHOLDER);

			assertThat(expectedResultAnnotation.failureMessage(),
					actualResult,
					is(expectedResultAnnotation.value()));
		}
	}

	@Test
	public void testValidateElement_withoutPlaceholder() throws CompilerMissingException {
		for (final Element element : withoutPlaceholderElements) {
			final ExpectedResult expectedResultAnnotation = element.getAnnotation(ExpectedResult.class);

			final String actualResult = buildInvocationLiteralFor((ExecutableElement) element);

			assertThat(expectedResultAnnotation.failureMessage(),
					actualResult,
					is(expectedResultAnnotation.value()));
		}
	}
}