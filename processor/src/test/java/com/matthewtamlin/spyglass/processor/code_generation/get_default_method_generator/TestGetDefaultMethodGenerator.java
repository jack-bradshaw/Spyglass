package com.matthewtamlin.spyglass.processor.code_generation.get_default_method_generator;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;

import com.matthewtamlin.avatar.rules.AvatarRule;
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
import com.matthewtamlin.spyglass.processor.code_generation.CallerDef;
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

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import static javax.lang.model.element.Modifier.STATIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class TestGetDefaultMethodGenerator {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/code_generation/" +
					"get_default_method_generator/Data.java")
			.build();

	private GetDefaultMethodGenerator generator;

	@Before
	public void setup() {
		final CoreHelpers coreHelpers = new CoreHelpers(
				avatarRule.getProcessingEnvironment().getElementUtils(),
				avatarRule.getProcessingEnvironment().getTypeUtils());

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
		final Element element = avatarRule.getElementWithUniqueId("boolean");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToBoolean.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.BOOLEAN.box());
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToBooleanResourceAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("boolean resource");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToBooleanResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.BOOLEAN.box());
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToColorResourceAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("color resource");
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
		final Element element = avatarRule.getElementWithUniqueId("color state list resource");
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
		final Element element = avatarRule.getElementWithUniqueId("dimension");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToDimension.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToDimensionResourceAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("dimension resource");
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
		final Element element = avatarRule.getElementWithUniqueId("drawable resource");
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
		final Element element = avatarRule.getElementWithUniqueId("enum constant");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToEnumConstant.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.OBJECT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToFloatAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("float");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToFloat.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToFractionResourceAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("fraction resource");
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
		final Element element = avatarRule.getElementWithUniqueId("integer");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToInteger.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToIntegerResourceAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("integer resource");
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
		final Element element = avatarRule.getElementWithUniqueId("null");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToNull.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, TypeName.OBJECT);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToStringAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("string");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, DefaultToString.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(String.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_defaultToStringResourceAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("string resource");
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
		final Element element = avatarRule.getElementWithUniqueId("text array");
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
		final Element element = avatarRule.getElementWithUniqueId("text");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
				element,
				DefaultToTextResource.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		assertThat(generatedMethod, is(notNullValue()));
		checkMethodSignature(generatedMethod, ClassName.get(CharSequence.class));
		checkCompiles(generatedMethod);
	}

	private void checkMethodSignature(final MethodSpec generatedMethod, final TypeName returnType) {
		assertThat("Generated method must not be null.", generatedMethod, is(notNullValue()));
		assertThat("Generated method has wrong return type.", generatedMethod.returnType, is(returnType));
		assertThat("Generated method has wrong number of parameters.", generatedMethod.parameters, hasSize(0));
		assertThat("Generated method must not be static.", generatedMethod.modifiers.contains(STATIC), is(false));
	}

	private void checkCompiles(final MethodSpec method) {
		final TypeSpec wrapperTypeSpec = CallerDef
				.getNewCallerSubclassPrototype("Wrapper", TypeName.OBJECT)
				.addMethod(CallerDef.getNewCallMethodPrototype().build())
				.addMethod(CallerDef.getNewConstructorPrototype(TypeName.OBJECT).build())
				.addMethod(method)
				.build();

		final JavaFile wrapperJavaFile = JavaFile
				.builder("", wrapperTypeSpec)
				.build();

		final Set<JavaFile> filesToCompile = new HashSet<>();
		filesToCompile.add(wrapperJavaFile);
		filesToCompile.add(CallerDef.SRC_FILE);

		CompileChecker.checkCompiles(filesToCompile);
	}
}