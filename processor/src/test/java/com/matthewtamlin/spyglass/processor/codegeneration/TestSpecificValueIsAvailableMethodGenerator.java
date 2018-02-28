/*
 * Copyright 2017-2018 Matthew David Tamlin
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

package com.matthewtamlin.spyglass.processor.codegeneration;

import com.google.testing.compile.JavaFileObjects;
import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificBooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificFlagHandler;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.framework.CompileChecker;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import java.util.HashSet;
import java.util.Set;

import static javax.lang.model.element.Modifier.STATIC;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class TestSpecificValueIsAvailableMethodGenerator {
  @Rule
  public final AvatarRule avatarRule = AvatarRule
      .builder()
      .withSourceFileObjects(
          JavaFileObjects.forResource(getClass().getResource("TestSpecificValueIsAvailableMethodGeneratorData.java")))
      .build();

  private SpecificValueIsAvailableMethodGenerator generator;

  @Before
  public void setup() {
    generator = new SpecificValueIsAvailableMethodGenerator(new AnnotationMirrorHelper(avatarRule.getElementUtils()));
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
  public void testGenerateFor_specificBooleanHandlerAnnotationSupplied() {
    final Element element = avatarRule.getElementWithUniqueId("specific boolean");

    final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(
        element,
        SpecificBooleanHandler.class);

    final MethodSpec generatedMethod = generator.generateFor(mirror);

    checkMethodSignature(generatedMethod);
    checkCompiles(generatedMethod);
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
  public void testGenerateFor_specificFlagHandlerAnnotationSupplied() {
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