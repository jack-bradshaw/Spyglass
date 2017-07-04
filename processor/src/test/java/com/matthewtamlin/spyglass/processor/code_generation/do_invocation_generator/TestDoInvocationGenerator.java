package com.matthewtamlin.spyglass.processor.code_generation.do_invocation_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.code_generation.DoInvocationGenerator;
import com.matthewtamlin.spyglass.processor.framework.CompileChecker;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for the {@link DoInvocationGenerator} class.
 * <p>
 * Cases covered by tests:
 * - Null case
 * - Call handler
 * -- With no args
 * -- With one arg
 * -- With multiple args
 * - Value handler
 * -- With one primitive number arg
 * -- With one primitive non-number arg
 * -- With one object number arg
 * -- With one object non-number arg
 * -- With only recipient
 * -- With recipient and one other arg
 * -- With recipient and multiple other args
 */
public class TestDoInvocationGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"do_invocation_generator/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private DoInvocationGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		generator = new DoInvocationGenerator(compilationRule.getElements(), compilationRule.getTypes());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullElementUtil() {
		new DoInvocationGenerator(null, mock(Types.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullTypeUtil() {
		new DoInvocationGenerator(mock(Elements.class), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nullElementSupplied() {
		generator.getMethod(null);
	}

	public void testGetMethod_elementHasCallHandler_noArgs() {
		final ExecutableElement element = getExecutableElementWithId("call handler, no args");

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	public void testGetMethod_elementHasCallHandler_oneArg() {
		final ExecutableElement element = getExecutableElementWithId("call handler, one arg");

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	public void testGetMethod_elementHasCallHandler_multipleArgs() {
		final ExecutableElement element = getExecutableElementWithId("call handler, multiple args");

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	public void testGetMethod_elementHasValueHandler_oneArg() {
		final ExecutableElement element = getExecutableElementWithId("value handler, one arg");

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	public void testGetMethod_elementHsaValueHandler_twoArgs_firstHasUseAnnotation() {
		final ExecutableElement element = getExecutableElementWithId("value handler, one arg, first has use");

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	public void testGetMethod_elementHsaValueHandler_twoArgs_secondHasUseAnnotation() {
		final ExecutableElement element = getExecutableElementWithId("value handler, one arg, second has use");

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	public void testGetMethod_elementHasValueHandler_multipleArgs() {
		final ExecutableElement element = getExecutableElementWithId("call handler, multiple args");

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	private ExecutableElement getExecutableElementWithId(final String id) {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}

	private void checkMethodSignature(final MethodSpec generatedMethod) {
		assertThat(generatedMethod.returnType, is(TypeName.VOID));

		assertThat(generatedMethod.parameters, hasSize(2));
		assertThat(generatedMethod.parameters.get(0).type, is((TypeName) ClassName.get(Data.class)));
		assertThat(generatedMethod.parameters.get(1).type, is((TypeName) ClassName.OBJECT));
	}

	private void checkCompiles(final MethodSpec methodSpec) {
		// Create a type to contain the method
		final TypeSpec wrapperTypeSpec = TypeSpec
				.classBuilder("Wrapper")
				.addMethod(methodSpec)
				.build();

		final JavaFile wrapperJavaFile = JavaFile
				.builder("", wrapperTypeSpec)
				.build();

		CompileChecker.checkCompiles(wrapperJavaFile);
	}
}