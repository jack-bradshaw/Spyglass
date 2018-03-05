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

import com.matthewtamlin.spyglass.processor.core.CompanionNamer;
import com.matthewtamlin.spyglass.processor.definitions.*;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.*;

import javax.inject.Inject;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Supplier;

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
    
    final MethodSpec setBuilderTarget = MethodSpec
        .methodBuilder("setTarget")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(targetTypeName, "target")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = target", builderTarget)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec setBuilderContext = MethodSpec
        .methodBuilder("setContext")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(AndroidClassNames.CONTEXT, "context")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = context", builderContext)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec setBuilderStyleableResource = MethodSpec
        .methodBuilder("setStyleableResource")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(ArrayTypeName.get(int[].class), "styleableResource")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = styleableResource", builderStyleableResource)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec setBuilderAttributeSet = MethodSpec
        .methodBuilder("setAttributeSet")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(AndroidClassNames.ATTRIBUTE_SET, "attributeSet")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = attributeSet", builderAttributeSet)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec setBuilderDefaultStyleAttribute = MethodSpec
        .methodBuilder("setDefaultStyleAttribute")
        .addModifiers(PUBLIC)
        .returns(builderTypeName)
        .addParameter(TypeName.INT, "defaultStyleAttribute")
        .addCode(CodeBlock
            .builder()
            .addStatement("this.$N = defaultStyleAttribute", builderDefaultStyleAttribute)
            .addStatement("return this")
            .build())
        .build();
    
    final MethodSpec setBuilderDefaultStyleResource = MethodSpec
        .methodBuilder("setDefaultStyleResource")
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
        .addMethod(setBuilderTarget)
        .addMethod(setBuilderContext)
        .addMethod(setBuilderStyleableResource)
        .addMethod(setBuilderAttributeSet)
        .addMethod(setBuilderDefaultStyleAttribute)
        .addMethod(setBuilderDefaultStyleResource)
        .addMethod(build)
        .build();
    
    final FieldSpec companionTarget = FieldSpec
        .builder(targetTypeName, "target", PRIVATE, FINAL)
        .build();
    
    final FieldSpec companionContext = FieldSpec
        .builder(AndroidClassNames.CONTEXT, "context", PRIVATE, FINAL)
        .build();
    
    final FieldSpec companionAttributesSupplier = FieldSpec
        .builder(
            ParameterizedTypeName.get(ClassName.get(Supplier.class), AndroidClassNames.TYPED_ARRAY),
            "attributesSupplier",
            PRIVATE,
            FINAL)
        .build();
    
    final Set<ExecutableElement> annotatedMethods = findAnnotatedElements(targetType);
    final Iterator<ExecutableElement> annotatedMethodsIterator = annotatedMethods.iterator();
    
    final ParameterSpec initialiseCallersAttributeParameter = ParameterSpec
        .builder(AndroidClassNames.TYPED_ARRAY, "attributes", FINAL)
        .build();
    
    final CodeBlock.Builder initialiseCallersCodeBuilder = CodeBlock
        .builder()
        .addStatement(
            "final $T callers = new $T()",
            ParameterizedTypeName.get(ClassName.get(List.class), CallerDef.getCallerAsClassName()),
            ParameterizedTypeName.get(ClassName.get(ArrayList.class), CallerDef.getCallerAsClassName()))
        .add("\n");
    
    while (annotatedMethodsIterator.hasNext()) {
      initialiseCallersCodeBuilder
          .addStatement(
              "callers.add($L)",
              callerGenerator.generateFor(
                  annotatedMethodsIterator.next(),
                  CodeBlock.of("$N", companionTarget),
                  CodeBlock.of("$N", companionContext),
                  CodeBlock.of("$N", initialiseCallersAttributeParameter)));
      
      if (annotatedMethodsIterator.hasNext()) {
        initialiseCallersCodeBuilder.add("\n");
      }
    }
    
    initialiseCallersCodeBuilder
        .add("\n")
        .addStatement("return callers");
    
    final MethodSpec initialiseCallers = MethodSpec
        .methodBuilder("initialiseCallers")
        .addModifiers(PRIVATE)
        .returns(ParameterizedTypeName.get(ClassName.get(List.class), CallerDef.getCallerAsClassName()))
        .addParameter(initialiseCallersAttributeParameter)
        .addCode(initialiseCallersCodeBuilder.build())
        .build();
    
    final MethodSpec callTargetMethods = CompanionDef
        .getNewCallTargetMethodsMethodPrototype()
        .addCode(CodeBlock
            .builder()
            .add("return $T\n", RxJavaClassNames.SINGLE)
            .add("\t\t.fromCallable(() -> $N.get())\n", companionAttributesSupplier)
            .add("\t\t.flatMapCompletable(attributes -> $T\n", RxJavaClassNames.OBSERVABLE)
            .add("\t\t\t\t.fromIterable($N(attributes))\n", initialiseCallers)
            .add("\t\t\t\t.flatMapCompletable($T::$N)\n", CallerDef.getCallerAsClassName(), CallerDef.CALL.name)
            .add("\t\t\t\t.andThen($T.fromRunnable(() -> attributes.recycle()))\n", RxJavaClassNames.COMPLETABLE)
            .add("\t\t\t\t.onErrorResumeNext(error -> {\n")
            .addStatement("\t\t\t\t\tattributes.recycle()")
            .add("\n")
            .addStatement("\t\t\t\t\treturn $T.error(error)", RxJavaClassNames.COMPLETABLE)
            .addStatement("\t\t\t\t}))")
            .build())
        .build();
    
    final MethodSpec callTargetMethodsNow = CompanionDef
        .getNewCallTargetMethodsNowMethodPrototype()
        .addCode(CodeBlock
            .builder()
            .addStatement("$N().blockingAwait()", callTargetMethods)
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
            .add("\n")
            .addStatement(
                "$N = () -> $N.obtainStyledAttributes(\n" +
                    "builder.$N,\n" +
                    "builder.$N,\n" +
                    "builder.$N,\n" +
                    "builder.$N)",
                companionAttributesSupplier,
                companionContext,
                builderAttributeSet,
                builderStyleableResource,
                builderDefaultStyleAttribute,
                builderDefaultStyleResource)
            .build())
        .build();
    
    final MethodSpec newBuilder = MethodSpec
        .methodBuilder("builder")
        .addModifiers(PUBLIC, STATIC)
        .returns(builderTypeName)
        .addCode("return new $T();\n", builderTypeName)
        .build();
    
    final TypeSpec companion = CompanionDef
        .getNewCompanionImplementationPrototype(companionName)
        .addField(companionTarget)
        .addField(companionContext)
        .addField(companionAttributesSupplier)
        .addMethod(companionConstructor)
        .addMethod(callTargetMethods)
        .addMethod(callTargetMethodsNow)
        .addMethod(initialiseCallers)
        .addMethod(newBuilder)
        .addType(builder)
        .build();
    
    return JavaFile
        .builder(elementUtil.getPackageOf(targetType).getQualifiedName().toString(), companion)
        .addFileComment("Generated by the Spyglass framework. Do not modify!")
        .indent("\t")
        .skipJavaLangImports(true)
        .build();
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
    
    handlerAnnotations.addAll(AnnotationRegistry.CONDITIONAL_HANDLERS);
    handlerAnnotations.addAll(AnnotationRegistry.UNCONDITIONAL_HANDLERS);
    handlerAnnotations.addAll(AnnotationRegistry.DEFAULTS);
    
    final Set<ExecutableElement> methodsWithAnnotations = new HashSet<>();
    
    for (final Element childElement : type.getEnclosedElements()) {
      for (final Class<? extends Annotation> annotationType : handlerAnnotations) {
        if (AnnotationMirrorHelper.getAnnotationMirror(childElement, annotationType) != null) {
          methodsWithAnnotations.add((ExecutableElement) childElement);
          
          break;
        }
      }
    }
    
    return methodsWithAnnotations;
  }
}
