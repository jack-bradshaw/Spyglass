package com.matthewtamlin.spyglass.processors.code_generation.invocation_literal_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.CompilerMissingException;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processors.code_generation.InvocationLiteralGenerator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.ExecutableElement;
import javax.tools.JavaFileObject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
public class TestInvocationLiteralGenerator {
	private static final File DATA_FILE = new File("processors/src/test/java/com/matthewtamlin/spyglass/processors" +
			"/code_generation/invocation_literal_generator/Data.java");

	@Rule
	public CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private InvocationLiteralGenerator generator;

	@Before
	public void setup() throws MalformedURLException, CompilerMissingException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		final JavaFileObject dataFileObject = JavaFileObjects.forResource(DATA_FILE.toURI().toURL());

		elementSupplier = new IdBasedElementSupplier(dataFileObject);
		generator = new InvocationLiteralGenerator(compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullElementUtil() {
		new InvocationLiteralGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateLiteralWithExtraArg_nullElement() {
		generator.generateLiteralWithExtraArg(null, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateLiteralWithExtraArg_nullExtraArgLiteral() throws CompilerMissingException {
		generator.generateLiteralWithExtraArg(mock(ExecutableElement.class), null);
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithNoUseAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("no use annotation with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, is("method(value)"));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseBooleanAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("boolean with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, is("method(false, value)"));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseByteAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("byte with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(
				invocationLiteral,
				either(is("method((byte)-100, value)"))
						.or(is("method((byte)0x9C, value)"))
						.or(is("method((byte)0x9c, value)")));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseCharAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("char with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(
				invocationLiteral,
				either(is("method(value, \'\\uF2FF\')"))
						.or(is("method(value, \'\\uf2ff\')"))
						.or(is("method(value, 0xF2FF)"))
						.or(is("method(value, 0xf2ff)"))
						.or(is("method(value, 62207)")));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseDoubleAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("double with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, is("method(-100.1, value)"));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseFloatAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("float with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, either(is("method(-100.1F, value)")).or(is("method(-100.1f, value)")));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseIntAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("int with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, is("method(-100, value)"));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseLongAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("long with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, either(is("method(-100L, value)")).or(is("method(-100l, value)")));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseNullAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("null with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, is("method(null, value)"));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseStringAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("string with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, is("method(\"hello world \\\\ __ \\\"quote\\\" // ! \\u00a9  \", value)"));
	}

	@Test
	public void testGenerateLiteralWithExtraArg_methodWithUseShortAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("short with extra arg");

		final String invocationLiteral = generator.generateLiteralWithExtraArg(element, "value");

		assertThat(invocationLiteral, is("method(-100, value)"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateLiteralWithoutExtraArg_nullElement() {
		generator.generateLiteralWithoutExtraArg(null);
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithNoUseAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("no use annotation without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, is("method()"));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseBooleanAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("boolean without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, is("method(true)"));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseByteAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("byte without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, either(is("method((byte)100)")).or(is("method((byte)0x64)")));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseCharAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("char without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(
				invocationLiteral,
				either(is("method(\'\\u1D11\')"))
						.or(is("method(\'\\u1d11\')"))
						.or(is("method(0x1D11)"))
						.or(is("method(0x1d11)"))
						.or(is("method(7441)")));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseDoubleAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("double without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, is("method(100.1)"));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseFloatAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("float without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, either(is("method(100.1F)")).or(is("method(100.1f)")));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseIntAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("int without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, is("method(100)"));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseLongAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("long without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, either(is("method(100L)")).or(is("method(100l)")));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseNullAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("null without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, is("method(null)"));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseShortAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("short without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, is("method(100)"));
	}

	@Test
	public void testGenerateLiteralWithoutExtraArg_methodWithUseStringAnnotation() throws CompilerMissingException {
		final ExecutableElement element = getExecutableElementWithId("string without extra arg");

		final String invocationLiteral = generator.generateLiteralWithoutExtraArg(element);

		assertThat(invocationLiteral, is("method(\"hello world \\\\ __ \\\"quote\\\" // ! \\u00a9  \")"));
	}

	private ExecutableElement getExecutableElementWithId(final String id) throws CompilerMissingException {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}
}