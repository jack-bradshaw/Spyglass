package com.matthewtamlin.spyglass.processor.code_generation.specific_value_is_available_method_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.processor.code_generation.SpecificValueIsAvailableMethodGenerator;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.framework.CompileChecker;
import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import static javax.lang.model.element.Modifier.STATIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestSpecificValueIsAvailableMethodGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"code_generation/specific_value_is_available_method_generator/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private SpecificValueIsAvailableMethodGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		final CoreHelpers coreHelpers = new CoreHelpers(compilationRule.getElements(), compilationRule.getTypes());
		generator = new SpecificValueIsAvailableMethodGenerator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullCoreHelpers() {
		new SpecificValueIsAvailableMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullSupplied() {
		generator.generateFor(null);
	}

	@Test
	public void testGenerateFor_specificEnumHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("specific enum");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, SpecificEnumHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_colorHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("specific flag");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, SpecificFlagHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	private void checkMethodSignature(final MethodSpec generatedMethod) {
		assertThat("Generated method must not be null.", generatedMethod, is(notNullValue()));
		assertThat("Generated method has wrong return type.", generatedMethod.returnType, is(TypeName.BOOLEAN.box()));
		assertThat("Generated method has wrong number of parameters.", generatedMethod.parameters.size(), is(0));
		assertThat("Generated method must not be static.", generatedMethod.modifiers.contains(STATIC), is(false));
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