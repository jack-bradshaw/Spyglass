package com.matthewtamlin.spyglass.processor.code_generation.caller_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processor.code_generation.CallerGenerator;
import com.matthewtamlin.spyglass.processor.testing_utils.CompileChecker;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestCallerGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
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

		callerGenerator.generateCaller(element);
	}

	@Test
	public void testGenerateCaller_elementWithCallHandler() {
		final ExecutableElement element = getExecutableElementWithId("call handler");

		final TypeSpec result = callerGenerator.generateCaller(element);

		assertThat(result, is(notNullValue()));
		checkCompiles(result);
	}

	@Test
	public void testGenerateCaller_elementWithValueHandlerButNoDefault() {
		final ExecutableElement element = getExecutableElementWithId("value handler no default");

		final TypeSpec result = callerGenerator.generateCaller(element);

		assertThat(result, is(notNullValue()));
		checkCompiles(result);
	}

	@Test
	public void testGenerateCaller_elementWithValueHandlerAndDefault() throws MalformedURLException {
		final ExecutableElement element = getExecutableElementWithId("value handler with default");

		final TypeSpec result = callerGenerator.generateCaller(element);

		assertThat(result, is(notNullValue()));
		checkCompiles(result);
	}

	private ExecutableElement getExecutableElementWithId(final String id) {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}

	private void checkCompiles(final TypeSpec anonymousTypeSpec) {
		// Anonymous class cannot be top level class, so nest the anonymous class as a field
		final TypeSpec wrapperTypeSpec = TypeSpec
				.classBuilder("Wrapper")
				.addField(FieldSpec
						.builder(TypeName.OBJECT, "o")
						.initializer("$L", anonymousTypeSpec)
						.build())
				.build();

		final JavaFile wrapperJavaFile = JavaFile
				.builder("", wrapperTypeSpec)
				.build();

		final Set<JavaFile> filesToCompile = new HashSet<>();

		filesToCompile.add(wrapperJavaFile);
		filesToCompile.add(CallerDef.getJavaFile());

		CompileChecker.checkCompiles(filesToCompile);
	}
}