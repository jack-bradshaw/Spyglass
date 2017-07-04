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
import com.matthewtamlin.spyglass.processor.testing_utils.CompileChecker;
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
import javax.lang.model.element.Modifier;

import static com.matthewtamlin.spyglass.processor.annotation_utils.AnnotationMirrorUtil.getAnnotationMirror;
import static com.matthewtamlin.spyglass.processor.code_generation.AndroidClassNames.CONTEXT;
import static com.matthewtamlin.spyglass.processor.code_generation.AndroidClassNames.TYPED_ARRAY;
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

		generator = new GetDefaultMethodGenerator(compilationRule.getElements());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullSupplied() {
		new GetDefaultMethodGenerator(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nullSupplied() {
		generator.getMethod(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetMethod_nonDefaultAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("no default annotation");
		final AnnotationMirror mirror = getAnnotationMirror(element, BooleanHandler.class);

		generator.getMethod(mirror);
	}

	@Test
	public void testGetMethod_defaultToBooleanAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("boolean");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToBoolean.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.BOOLEAN);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToBooleanResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("boolean resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToBooleanResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.BOOLEAN);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToColorResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToColorResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.INT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToColorStatListResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("color state list resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToColorStateListResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(ColorStateList.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToDimensionAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("dimension");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToDimension.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.FLOAT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToDimensionResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("dimension resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToDimensionResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.FLOAT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToDrawableResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("drawable resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToDrawableResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Drawable.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToEnumConstantAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("enum constant");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToEnumConstant.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.OBJECT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToFloatAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("float");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToFloat.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.FLOAT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToFractionResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("fraction resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToFractionResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.FLOAT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToIntegerAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("integer");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToInteger.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.INT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToIntegerResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("integer resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToIntegerResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.INT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToNullAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("null");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToNull.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.OBJECT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToStringAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("string");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToString.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(String.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToStringResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("string resource");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToStringResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(String.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToTextArrayResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("text array");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToTextArrayResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.get(CharSequence[].class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGetMethod_defaultToTextResourceAnnotationSupplied() {
		final Element element = elementSupplier.getUniqueElementWithId("text");
		final AnnotationMirror mirror = getAnnotationMirror(element, DefaultToTextResource.class);

		final MethodSpec generatedMethod = generator.getMethod(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(CharSequence.class));
		checkCompiles(generatedMethod);
	}

	private void checkMethodSignature(final MethodSpec generatedMethod, final TypeName returnType) {
		assertThat(generatedMethod.returnType, is(returnType));
		assertThat(generatedMethod.parameters, hasSize(2));
		assertThat(generatedMethod.parameters.get(0).type, is((TypeName) CONTEXT));
		assertThat(generatedMethod.parameters.get(1).type, is((TypeName) TYPED_ARRAY));
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