package com.matthewtamlin.spyglass.processor.code_generation.get_value_method_generator;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.processor.code_generation.GetValueMethodGenerator;
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

import static com.matthewtamlin.spyglass.processor.code_generation.AndroidClassNames.TYPED_ARRAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestGetValueMethodGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processor/" +
			"code_generation/get_value_method_generator/Data.java");

	@Rule
	public final CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private GetValueMethodGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));
		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));

		final CoreHelpers coreHelpers = new CoreHelpers(compilationRule.getElements(), compilationRule.getTypes());
		generator = new GetValueMethodGenerator(coreHelpers);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullCoreHelpers() {
		new GetValueMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nullSupplied() {
		generator.getMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nonValueHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("no value handler");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToBoolean.class);

		generator.getMethod(mirror);
	}

	@Test
	public void testGetMethod_booleanHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("boolean");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, BooleanHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.BOOLEAN);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_colorHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, ColorHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.INT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_colorStateListHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color state list");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, ColorStateListHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(ColorStateList.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_dimensionHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("dimension");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DimensionHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.FLOAT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_drawableHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("drawable");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DrawableHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Drawable.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_enumConstantHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum constant");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, EnumConstantHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.OBJECT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_EnumOrdinalHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum ordinal");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, EnumOrdinalHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.INT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_floatHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("float");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, FloatHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.FLOAT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_fractionHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("fraction");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, FractionHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.FLOAT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_integerHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("integer");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, IntegerHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.INT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_stringHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("string");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, StringHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(String.class));
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