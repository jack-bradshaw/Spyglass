package com.matthewtamlin.spyglass.processors.code_generation.specific_value_is_available_method_generator;

import com.google.testing.compile.CompilationRule;
import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.element_supplier.IdBasedElementSupplier;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
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
import com.matthewtamlin.spyglass.processors.code_generation.SpecificValueIsAvailableMethodGenerator;
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

public class TestSpecificValueIsAvailableMethodGenerator {
	private static final File DATA_FILE = new File("processor/src/test/java/com/matthewtamlin/spyglass/processors/" +
			"code_generation/specific_value_is_available_method_generator/Data.java");

	@Rule
	public CompilationRule compilationRule = new CompilationRule();

	private IdBasedElementSupplier elementSupplier;

	private SpecificValueIsAvailableMethodGenerator generator;

	@Before
	public void setup() throws MalformedURLException {
		assertThat("Data file does not exist.", DATA_FILE.exists(), is(true));

		elementSupplier = new IdBasedElementSupplier(JavaFileObjects.forResource(DATA_FILE.toURI().toURL()));
		generator = new SpecificValueIsAvailableMethodGenerator(compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullSupplied() {
		new SpecificValueIsAvailableMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nullSupplied() {
		generator.getMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nonCallHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("no call handler");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToBoolean.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);
	}

	@Test
	public void testGetMethod_specificEnumHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("specific enum");
		final AnnotationMirror mirror = getAnnotationMirror(element, SpecificEnumHandler.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod);
	}

	@Test
	public void testGetMethod_colorHandlerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("specific flag");
		final AnnotationMirror mirror = getAnnotationMirror(element, SpecificFlagHandler.class);

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