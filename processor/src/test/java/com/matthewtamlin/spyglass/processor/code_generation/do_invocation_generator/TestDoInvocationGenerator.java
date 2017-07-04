package com.matthewtamlin.spyglass.processor.code_generation.do_invocation_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.code_generation.DoInvocationGenerator;
import com.matthewtamlin.spyglass.processor.testing_utils.CompileChecker;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
@RunWith(JUnit4.class)
public class TestDoInvocationGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"code_generation/do_invocation_generator/Data.java");

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

	@Test
	public void testGetMethod_callHandler_noArgs() {
		doTestForCallHandlerElementWithId("call handler, no args");
	}

	@Test
	public void testGetMethod_callHandler_oneArg() {
		doTestForCallHandlerElementWithId("call handler, one arg");
	}

	@Test
	public void testGetMethod_callHandler_multipleArgs() {
		doTestForCallHandlerElementWithId("call handler, multiple args");
	}

	@Test
	public void testGetMethod_valueHandler_primitiveNumberArg() {
		doTestForValueHandlerElementWithId("value handler, primitive number arg");
	}

	@Test
	public void testGetMethod_valueHandler_primitiveNonNumberArg() {
		doTestForValueHandlerElementWithId("value handler, primitive non-number arg");
	}

	@Test
	public void testGetMethod_valueHandler_primitiveCharArg() {
		doTestForValueHandlerElementWithId("value handler, primitive char arg");
	}

	@Test
	public void testGetMethod_valueHandler_objectNumberArg() {
		doTestForValueHandlerElementWithId("value handler, object number arg");
	}

	@Test
	public void testGetMethod_valueHandler_objectNonNumberArg() {
		doTestForValueHandlerElementWithId("value handler, object non-number arg");
	}

	@Test
	public void testGetMethod_valueHandler_objectCharacterArg() {
		doTestForValueHandlerElementWithId("value handler, object character arg");
	}

	@Test
	public void testGetMethod_valueHandler_multipleArgs() {
		doTestForValueHandlerElementWithId("value handler, multiple args");
	}

	private void doTestForCallHandlerElementWithId(final String id) {
		final ExecutableElement element = getExecutableElementWithId(id);

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		assertThat(generatedMethod.returnType, is(TypeName.VOID));
		assertThat(generatedMethod.parameters, hasSize(1));
		assertThat(generatedMethod.parameters.get(0).type, is((TypeName) ClassName.get(Data.class)));

		checkCompiles(generatedMethod);
	}

	private void doTestForValueHandlerElementWithId(final String id) {
		final ExecutableElement element = getExecutableElementWithId(id);

		final MethodSpec generatedMethod = generator.getMethod(element);

		assertThat(generatedMethod, is(notNullValue()));
		assertThat(generatedMethod.returnType, is(TypeName.VOID));
		assertThat(generatedMethod.parameters, hasSize(2));
		assertThat(generatedMethod.parameters.get(0).type, is((TypeName) ClassName.get(Data.class)));
		assertThat(generatedMethod.parameters.get(1).type, is((TypeName) TypeName.OBJECT));

		checkCompiles(generatedMethod);
	}

	private ExecutableElement getExecutableElementWithId(final String id) {
		try {
			return (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
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