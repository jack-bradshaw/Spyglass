package com.matthewtamlin.spyglass.processors.code_generation.caller_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processors.code_generation.CallerGenerator;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.ExecutableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestCallerGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processors/" +
			"code_generation/caller_generator/Data.java");
	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private CallerGenerator callerGenerator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		callerGenerator = new CallerGenerator(compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullSupplied() {
		new CallerGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateCaller_nullSupplied() {
		callerGenerator.generateCaller(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateCaller_elementWithNoHandlerAnnotation() {
		final ExecutableElement element = getExecutableElementWithId("no handler");

		final TypeSpec result = callerGenerator.generateCaller(element);
	}

	@Test
	public void testGenerateCaller_elementWithCallHandler() {
		final ExecutableElement element = getExecutableElementWithId("call handler");

		final TypeSpec result = callerGenerator.generateCaller(element);

		assertThat(result, is(notNullValue()));
	}

	@Test
	public void testGenerateCaller_elementWithValueHandlerButNoDefault() {
		final ExecutableElement element = getExecutableElementWithId("value handler no default");

		final TypeSpec result = callerGenerator.generateCaller(element);

		assertThat(result, is(notNullValue()));
	}

	@Test
	public void testGenerateCaller_elementWithValueHandlerAndDefault() {
		final ExecutableElement element = getExecutableElementWithId("value handler with default");

		final TypeSpec result = callerGenerator.generateCaller(element);

		assertThat(result, is(notNullValue()));
	}

	private ExecutableElement getExecutableElementWithId(final String id) {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}
}