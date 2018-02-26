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

package com.matthewtamlin.spyglass.processor.codegeneration;

import com.matthewtamlin.spyglass.processor.core.CompanionNamer;
import com.matthewtamlin.spyglass.processor.definitions.*;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.Modifier.*;

public class CompanionGenerator {
  private CallerGenerator callerGenerator;
  
  private Elements elementUtil;
  
  @Inject
  public CompanionGenerator(final CallerGenerator callerGenerator, final Elements elementUtil) {
    this.callerGenerator = checkNotNull(callerGenerator);
    this.elementUtil = checkNotNull(elementUtil);
  }
  
  public JavaFile generateFor(final TypeElement targetType) {
    final String companionName = CompanionNamer.getCompanionNameFor(targetType);
    final String builderName = "Builder";
    
    final TypeName targetTypeName = ClassName.get(targetType);
    final TypeName companionTypeName = ClassName.get(getPackage(targetType).toString(), companionName);
    final TypeName builderTypeName = ClassName.get(getPackage(targetType).toString(), companionName, builderName);
    
    final FieldSpec builderTarget = FieldSpec
        .builder(targetTypeName, "target", PRIVATE)
        .build();
    
    final FieldSpec builderContext = FieldSpec
        .builder(AndroidClassNames.CONTEXT, "context", PRIVATE)
        .build();
    
    final FieldSpec builderStyleableResource = FieldSpec
        .builder(ArrayTypeName.get(int[].class), "styleableResource", PRIVATE)
        .build();
    
    final FieldSpec builderAttributeSet = FieldSpec
        .builder(AndroidClassNames.ATTRIBUTE_SET, "attributeSet", PRIVATE)
        .build();
    
    final FieldSpec builderDefaultStyleAttribute = FieldSpec
        .builder(TypeName.INT, "defaultStyleAttribute", PRIVATE)
        .build();
    
    final FieldSpec builderDefaultStyleResource = FieldSpec
        .builder(TypeName.INT, "defaultStyleResource", PRIVATE)
        .build();
    
    final MethodSpec builderConstructor = MethodSpec
        .constructorBuilder()
        .addModifiers(PRIVATE)
        .build();
    
    final MethodSpec withTarget = MethodSpec
        .methodBuilder("withTarget")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(targetTypeName, "target")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = target", builderTarget)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec withContext = MethodSpec
        .methodBuilder("withContext")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(AndroidClassNames.CONTEXT, "context")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = context", builderContext)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec withStyleableResource = MethodSpec
        .methodBuilder("withStyleableResource")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(ArrayTypeName.get(int[].class), "styleableResource")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = styleableResource", builderStyleableResource)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec withAttributeSet = MethodSpec
        .methodBuilder("withAttributeSet")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(AndroidClassNames.ATTRIBUTE_SET, "attributeSet")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = attributeSet", builderAttributeSet)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec withDefaultStyleAttribute = MethodSpec
        .methodBuilder("withDefaultStyleAttribute")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(TypeName.INT, "defaultStyleAttribute")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = defaultStyleAttribute", builderDefaultStyleAttribute)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec withDefaultStyleResource = MethodSpec
        .methodBuilder("withDefaultStyleResource")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(TypeName.INT, "defaultStyleResource")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = defaultStyleResource", builderDefaultStyleResource)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec build = MethodSpec
        .methodBuilder("build")
        .addModifiers(PUBLIC)
        .returns(companionTypeName)
        .addCode(CodeBlock
            .builder()
            .beginControlFlow("if ($N == null)", builderTarget)
            .addStatement(
                "throw new $T($S)",
                IllegalStateException.class,
                "A target must be set before calling build().")
            .endControlFlow()
            .add("\n")
            .beginControlFlow("if ($N == null)", builderContext)
            .addStatement(
                "throw new $T($S)",
                IllegalStateException.class,
                "A context must be set before calling build().")
            .endControlFlow()
            .add("\n")
            .beginControlFlow("if ($N == null)", builderStyleableResource)
            .addStatement(
                "throw new $T($S)",
                IllegalStateException.class,
                "A styleable resource must be set before calling build().")
            .endControlFlow()
            .add("\n")
            .addStatement("return new $T(this)", companionTypeName)
            .build())
        .build();
    
    final TypeSpec builder = TypeSpec
        .classBuilder(builderName)
        .addModifiers(PUBLIC, STATIC)
        .addField(builderTarget)
        .addField(builderContext)
        .addField(builderStyleableResource)
        .addField(builderAttributeSet)
        .addField(builderDefaultStyleAttribute)
        .addField(builderDefaultStyleResource)
        .addMethod(builderConstructor)
        .addMethod(withTarget)
        .addMethod(withContext)
        .addMethod(withStyleableResource)
        .addMethod(withAttributeSet)
        .addMethod(withDefaultStyleAttribute)
        .addMethod(withDefaultStyleResource)
        .addMethod(build)
        .build();
    
    final FieldSpec callers = FieldSpec
        .builder(listOfCallers(), "callers", PRIVATE, FINAL)
        .initializer("new $T();", arrayListOfCallers())
        .build();
    
    final FieldSpec companionTarget = FieldSpec
        .builder(targetTypeName, "target", PRIVATE, FINAL)
        .build();
    
    final FieldSpec companionContext = FieldSpec
        .builder(AndroidClassNames.CONTEXT, "context", PRIVATE, FINAL)
        .build();
    
    final FieldSpec companionAttributes = FieldSpec
        .builder(AndroidClassNames.TYPED_ARRAY, "attributes", PRIVATE, FINAL)
        .build();
    
    final FieldSpec companionHasBeenUsed = FieldSpec
        .builder(AtomicBoolean.class, "companionHasBeenUsed", PRIVATE, FINAL)
        .initializer("new $T(false);", AtomicBoolean.class)
        .build();
    
    final CodeBlock.Builder initialiseCallersCodeBuilder = CodeBlock.builder();
    final Set<ExecutableElement> annotatedMethods = findAnnotatedElements(targetType);
    final Iterator<ExecutableElement> annotatedMethodsIterator = annotatedMethods.iterator();
    
    while (annotatedMethodsIterator.hasNext()) {
      initialiseCallersCodeBuilder
          .addStatement(
              "$N.add($L)",
              callers,
              callerGenerator.generateFor(
                  annotatedMethodsIterator.next(),
                  CodeBlock.of("$N", companionTarget),
                  CodeBlock.of("$N", companionContext),
                  CodeBlock.of("$N", companionAttributes)));
      
      if (annotatedMethodsIterator.hasNext()) {
        initialiseCallersCodeBuilder.add("\n");
      }
    }
    
    final MethodSpec initialiseCallers = MethodSpec
        .methodBuilder("initialiseCallers")
        .addModifiers(PRIVATE)
        .returns(void.class)
        .addCode(initialiseCallersCodeBuilder.build())
        .build();
    
    final MethodSpec activateCallers = CompanionDef
        .getNewActivateCallersMethodPrototype()
        .addCode(CodeBlock
            .builder()
            .beginControlFlow("if (!$N.compareAndSet(false, true))", companionHasBeenUsed)
            .addStatement(
                "throw new $T($S)",
                RuntimeException.class,
                "This companion has already been used. Each instance can only be used once.")
            .endControlFlow()
            .add("\n")
            .beginControlFlow("for (final $T caller : $N)", CallerDef.getCallerAsClassName(), callers)
            .beginControlFlow("try")
            .addStatement("caller.$N()", CallerDef.CALL)
            .nextControlFlow("catch (final $T t)", Throwable.class)
            .addStatement("$N.recycle()", companionAttributes)
            .add("\n")
            .addStatement("throw new $T(t)", TargetExceptionDef.getTargetExceptionAsClassname())
            .endControlFlow()
            .endControlFlow()
            .add("\n")
            .addStatement("$N.recycle()", companionAttributes)
            .build())
        .build();
    
    final MethodSpec companionConstructor = MethodSpec
        .constructorBuilder()
        .addModifiers(PRIVATE)
        .addParameter(builderTypeName, "builder", FINAL)
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = builder.$N", companionTarget, builderTarget)
            .addStatement("this.$N = builder.$N", companionContext, builderContext)
            .addStatement(
                "this.$N = $N.obtainStyledAttributes(\n" +
                    "builder.$N,\n" +
                    "builder.$N,\n" +
                    "builder.$N,\n" +
                    "builder.$N)",
                companionAttributes,
                companionContext,
                builderAttributeSet,
                builderStyleableResource,
                builderDefaultStyleAttribute,
                builderDefaultStyleResource)
            .add("\n")
            .addStatement("$N()", initialiseCallers)
            .build())
        .build();
    
    final MethodSpec getBuilder = MethodSpec
        .methodBuilder("builder")
        .addModifiers(PUBLIC, STATIC)
        .returns(builderTypeName)
        .addCode("return new $T();\n", builderTypeName)
        .build();
    
    final TypeSpec companion = CompanionDef
        .getNewCompanionImplementationPrototype(companionName)
        .addField(callers)
        .addField(companionTarget)
        .addField(companionContext)
        .addField(companionAttributes)
        .addField(companionHasBeenUsed)
        .addMethod(companionConstructor)
        .addMethod(activateCallers)
        .addMethod(initialiseCallers)
        .addMethod(getBuilder)
        .addType(builder)
        .build();
    
    return JavaFile
        .builder(elementUtil.getPackageOf(targetType).getQualifiedName().toString(), companion)
        .addFileComment("Generated by the Spyglass framework. Do not modify!")
        .indent("\t")
        .skipJavaLangImports(true)
        .build();
  }
  
  private TypeName listOfCallers() {
    final ClassName genericList = ClassName.get(List.class);
    
    return ParameterizedTypeName.get(genericList, CallerDef.getCallerAsClassName());
  }
  
  private TypeName arrayListOfCallers() {
    final ClassName genericArrayList = ClassName.get(ArrayList.class);
    
    return ParameterizedTypeName.get(genericArrayList, CallerDef.getCallerAsClassName());
  }
  
  private PackageElement getPackage(final TypeElement type) {
    if (type.getNestingKind() == NestingKind.TOP_LEVEL) {
      return (PackageElement) type.getEnclosingElement();
    } else {
      return getPackage((TypeElement) type.getEnclosingElement());
    }
  }
  
  private Set<ExecutableElement> findAnnotatedElements(final TypeElement type) {
    final Set<Class<? extends Annotation>> handlerAnnotations = new HashSet<>();
    
    handlerAnnotations.addAll(AnnotationRegistry.CALL_HANDLER_ANNOS);
    handlerAnnotations.addAll(AnnotationRegistry.VALUE_HANDLER_ANNOS);
    handlerAnnotations.addAll(AnnotationRegistry.DEFAULT_ANNOS);
    
    final Set<ExecutableElement> methodsWithAnnotations = new HashSet<>();
    
    for (final Element childElement : type.getEnclosedElements()) {
      for (final Class<? extends Annotation> annoType : handlerAnnotations) {
        if (AnnotationMirrorHelper.getAnnotationMirror(childElement, annoType) != null) {
          methodsWithAnnotations.add((ExecutableElement) childElement);
          
          break;
        }
      }
    }
    
    return methodsWithAnnotations;
  }
}
