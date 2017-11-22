/*
 * Copyright 2017 Matthew David Tamlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.matthewtamlin.spyglass.processor.code_generation.get_placeholder_method_generator;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
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

import java.util.HashSet;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;

import static javax.lang.model.element.Modifier.STATIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;

public class TestGetPlaceholderMethodGenerator {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/code_generation/" +
					"get_placeholder_method_generator/Data.java")
			.build();

	private GetPlaceholderMethodGenerator generator;

	@Before
	public void setup() {
		final CoreHelpers coreHelpers = new CoreHelpers(avatarRule.getElementUtils(), avatarRule.getTypeUtils());
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

	@Test(expected = IllegalArgumentException.class)
	public void testGenerateFor_negativeParameterIndex() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("boolean");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		generator.generateFor(useAnnotation, -1);
	}

	@Test
	public void testGenerateFor_zeroParameterIndex() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("boolean");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		generator.generateFor(useAnnotation, 0);
	}

	@Test
	public void testGenerateFor_positiveParameterIndex() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("boolean");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		generator.generateFor(useAnnotation, 1);
	}

	@Test
	public void testGenerateFor_parameterWithUseBoolean() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("boolean");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Boolean.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseByte() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("byte");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseChar() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("char");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Character.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseDouble() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("double");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseFloat() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("float");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseInt() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("int");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseLong() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("long");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseNull() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("null");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Object.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseShort() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("short");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(Number.class));
		checkCompiles(generatedMethod);
	}

	@Test
	public void testGenerateFor_parameterWithUseString() {
		final VariableElement parameter = avatarRule.getElementWithUniqueId("string");
		final AnnotationMirror useAnnotation = UseAnnoRetriever.getAnnotation(parameter);

		final MethodSpec generatedMethod = generator.generateFor(useAnnotation, 0);

		checkSignature(generatedMethod, ClassName.get(String.class));
		checkCompiles(generatedMethod);
	}

	private void checkSignature(final MethodSpec generatedMethod, final TypeName returnType) {
		assertThat("Generated method must not be null.", generatedMethod, is(notNullValue()));
		assertThat("Generated method has wrong return type.", generatedMethod.returnType, is(returnType));
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