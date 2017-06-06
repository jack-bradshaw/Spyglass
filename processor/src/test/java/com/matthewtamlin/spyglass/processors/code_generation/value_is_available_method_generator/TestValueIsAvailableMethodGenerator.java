package com.matthewtamlin.spyglass.processors.code_generation.value_is_available_method_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.TextArrayHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.TextHandler;
import com.matthewtamlin.spyglass.processors.code_generation.GetValueMethodGenerator;
import com.matthewtamlin.spyglass.processors.code_generation.ValueIsAvailableMethodGenerator;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import static com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil.getAnnotationMirror;
import static com.matthewtamlin.spyglass.processors.code_generation.AndroidClassNames.TYPED_ARRAY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestValueIsAvailableMethodGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processors/" +
			"code_generation/value_is_available_method_generator/Data.java");

	@Rule
	public CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private ValueIsAvailableMethodGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));
		generator = new ValueIsAvailableMethodGenerator(compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullSupplied() {
		new GetValueMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nullSupplied() {
		generator.getMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nonValueHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("no value handler");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToBoolean.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);
	}

	@Test
	public void testGetMethod_booleanHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("boolean");
		final AnnotationMirror mirror = getAnnotationMirror(element, BooleanHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_colorHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color");
		final AnnotationMirror mirror = getAnnotationMirror(element, ColorHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_colorStateListHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color state list");
		final AnnotationMirror mirror = getAnnotationMirror(element, ColorStateListHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_dimensionHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("dimension");
		final AnnotationMirror mirror = getAnnotationMirror(element, DimensionHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_drawableHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("drawable");
		final AnnotationMirror mirror = getAnnotationMirror(element, DrawableHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_enumConstantHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum constant");
		final AnnotationMirror mirror = getAnnotationMirror(element, EnumConstantHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_EnumOrdinalHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum ordinal");
		final AnnotationMirror mirror = getAnnotationMirror(element, EnumOrdinalHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_floatHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("float");
		final AnnotationMirror mirror = getAnnotationMirror(element, FloatHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_fractionHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("fraction");
		final AnnotationMirror mirror = getAnnotationMirror(element, FractionHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_integerHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("integer");
		final AnnotationMirror mirror = getAnnotationMirror(element, IntegerHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_stringHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("string");
		final AnnotationMirror mirror = getAnnotationMirror(element, StringHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_textArrayHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("text array");
		final AnnotationMirror mirror = getAnnotationMirror(element, TextArrayHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_textHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("text");
		final AnnotationMirror mirror = getAnnotationMirror(element, TextHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	private void checkMethodSignature(final MethodSpec generatedMethod) {
		assertThat(generatedMethod.hasModifier(Modifier.PUBLIC), is(true));
		assertThat(generatedMethod.returnType, is(TypeName.BOOLEAN));
		assertThat(generatedMethod.parameters, hasSize(1));
		assertThat(generatedMethod.parameters.get(0).type, is((TypeName) TYPED_ARRAY));
	}
}