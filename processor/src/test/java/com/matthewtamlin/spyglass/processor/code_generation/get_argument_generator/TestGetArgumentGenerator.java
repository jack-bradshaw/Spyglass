package com.matthewtamlin.spyglass.processor.code_generation.get_argument_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;
import com.matthewtamlin.spyglass.processor.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processor.code_generation.GetPlaceholderMethodGenerator;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
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
import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.CombinableMatcher.either;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

public class TestGetArgumentGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"code_generation/get_argument_generator/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private GetPlaceholderMethodGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		final CoreHelpers coreHelpers = new CoreHelpers(compilationRule.getElements(), compilationRule.getTypes());
		generator = new GetPlaceholderMethodGenerator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullCoreHelpers() {
		new GetPlaceholderMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullParameter() {
		generator.generateFor(null, 0);
	}

	@Test
	public void testGenerateFor_negativeParameterIndex() {
		generator.generateFor(mock(AnnotationMirror.class), -1);
	}

	@Test
	public void testGenerateFor_zeroParameterIndex() {
		generator.generateFor(mock(AnnotationMirror.class), 0);
	}

	@Test
	public void testGenerateFor_positiveParameterIndex() {
		generator.generateFor(mock(AnnotationMirror.class), 1);
	}

	@Test
	public void testGenerateFor_parameterWithUseBoolean() {
		final VariableElement parameter = getFirstParameterFromElementWithId("boolean");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		assertThat(generatedMethod.returnType, either(is(TypeName.BOOLEAN)).or(is(TypeName.BOOLEAN.box())));
		assertThat(generatedMethod.parameters.size(), is(0));

		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseByte() {
		final VariableElement parameter = getFirstParameterFromElementWithId("byte");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		checkSignatureNumberCase(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseChar() {
		final VariableElement parameter = getFirstParameterFromElementWithId("char");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		checkSignatureNumberCase(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseDouble() {
		final VariableElement parameter = getFirstParameterFromElementWithId("double");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		checkSignatureNumberCase(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseFloat() {
		final VariableElement parameter = getFirstParameterFromElementWithId("float");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		checkSignatureNumberCase(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseInt() {
		final VariableElement parameter = getFirstParameterFromElementWithId("int");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		checkSignatureNumberCase(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseLong() {
		final VariableElement parameter = getFirstParameterFromElementWithId("long");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		checkSignatureNumberCase(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseNull() {
		final VariableElement parameter = getFirstParameterFromElementWithId("null");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		assertThat(generatedMethod.returnType, is((TypeName) TypeName.OBJECT));
		assertThat(generatedMethod.parameters.size(), is(0));

		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseShort() {
		final VariableElement parameter = getFirstParameterFromElementWithId("short");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		checkSignatureNumberCase(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseString() {
		final VariableElement parameter = getFirstParameterFromElementWithId("string");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		assertThat(generatedMethod, is(notNullValue()));
		assertThat(generatedMethod.returnType, is((TypeName) ClassName.get(String.class)));
		assertThat(generatedMethod.parameters.size(), is(0));

		checkCompiles(generatedMethod);
	}

	private VariableElement getFirstParameterFromElementWithId(final String id) {
		try {
			final ExecutableElement method = (ExecutableElement) elementSupplier.getUniqueElementWithId(id);
			return method.getParameters().get(0);
		} catch (final ClassCastException e) {
			throw new RuntimeException("Found element with ID " + id + ", but it wasn't an ExecutableElement.");
		}
	}

	private void checkCompiles(final MethodSpec method) {
		final TypeSpec wrapperTypeSpec = TypeSpec
				.classBuilder("Wrapper")
				.superclass(CallerDef.getCallerAsClassName())
				.addMethod(method)
				.addMethod(CallerDef.getNewCallMethodPrototype().build())
				.addMethod(CallerDef.getNewConstructorPrototype(TypeName.OBJECT).build())
				.build();

		final JavaFile wrapperJavaFile = JavaFile
				.builder("", wrapperTypeSpec)
				.build();

		final Set<JavaFile> filesToCompile = new HashSet<>();
		filesToCompile.add(wrapperJavaFile);
		filesToCompile.add(CallerDef.SRC_FILE);

		CompileChecker.checkCompiles(filesToCompile);
	}

	private void checkSignatureNumberCase(final MethodSpec method) {
		assertThat("Expected Number return type.", method.returnType, is((TypeName) ClassName.get(Number.class)));
		assertThat("Expected no parameters.", method.parameters.size(), is(0));
	}
}