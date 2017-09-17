package com.matthewtamlin.spyglass.processor.code_generation.specific_value_is_available_method_generator;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.common.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.common.class_definitions.CallerDef;
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

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

import static javax.lang.model.element.Modifier.STATIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class TestSpecificValueIsAvailableMethodGenerator {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/code_generation/" +
					"specific_value_is_available_method_generator/Data.java")
			.build();

	private SpecificValueIsAvailableMethodGenerator generator;

	@Before
	public void setup() {
		final CoreHelpers coreHelpers = new CoreHelpers(avatarRule.getElementUtils(), avatarRule.getTypeUtils());

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
		final Element element = avatarRule.getElementWithUniqueId("specific enum");
		final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, SpecificEnumHandler.class);

		final MethodSpec generatedMethod = generator.generateFor(mirror);

		checkMethodSignature(generatedMethod);
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_colorHandlerAnnotationSupplied() {
		final Element element = avatarRule.getElementWithUniqueId("specific flag");
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