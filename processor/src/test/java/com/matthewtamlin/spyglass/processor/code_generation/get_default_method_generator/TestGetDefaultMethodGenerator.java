package com.matthewtamlin.spyglass.processor.code_generation.get_default_method_generator;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.processor.code_generation.GetDefaultMethodGenerator;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.framework.CompileChecker;
import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
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

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestGetDefaultMethodGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"code_generation/get_default_method_generator/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private GetDefaultMethodGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		final CoreHelpers coreHelpers = new CoreHelpers(compilationRule.getElements(), compilationRule.getTypes());
		generator = new GetDefaultMethodGenerator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullCoreHelpers() {
		new GetDefaultMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_nullSupplied() {
		generator.generateFor(null);
	}

	@Test
	public void testGenerateFor_defaultToBooleanAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("boolean");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToBoolean.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.BOOLEAN);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToBooleanResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("boolean resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToBooleanResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.BOOLEAN);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToColorResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToColorResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToColorStatListResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color state list resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToColorStateListResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(ColorStateList.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToDimensionAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("dimension");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToDimension.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToDimensionResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("dimension resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToDimensionResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToDrawableResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("drawable resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToDrawableResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Drawable.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToEnumConstantAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum constant");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToEnumConstant.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Data.PlaceholderEnum.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToFloatAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("float");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToFloat.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToFractionResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("fraction resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToFractionResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToIntegerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("integer");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToInteger.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToIntegerResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("integer resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToIntegerResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToNullAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("null");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToNull.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.OBJECT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToStringAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("string");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToString.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(String.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToStringResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("string resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToStringResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(String.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToTextArrayResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("text array");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToTextArrayResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.get(CharSequence[].class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToTextResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("text");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToTextResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(CharSequence.class));
		checkCompiles(generatedMethod);
	}

	private void checkMethodSignature(final MethodSpec generatedMethod, final TypeName returnType) {
		assertThat(generatedMethod.returnType, is(returnType));
		assertThat(generatedMethod.parameters, hasSize(0));
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