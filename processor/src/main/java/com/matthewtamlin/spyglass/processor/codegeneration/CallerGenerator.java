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

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.annotationretrievers.ConditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.DefaultRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.PlaceholderRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.UnconditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class CallerGenerator {
  private final GetDefaultMethodGenerator getDefaultMethodGenerator;
  
  private final GetValueMethodGenerator getValueMethodGenerator;
  
  private final GetPlaceholderMethodGenerator getPlaceholderMethodGenerator;
  
  private final AnyValueIsAvailableMethodGenerator anyValueIsAvailableGenerator;
  
  private final SpecificValueIsAvailableMethodGenerator specificValueIsAvailableGenerator;
  
  private CastWrapperGenerator castWrapperGenerator;
  
  @Inject
  public CallerGenerator(
      final GetDefaultMethodGenerator getDefaultMethodGenerator,
      final GetValueMethodGenerator getValueMethodGenerator,
      final GetPlaceholderMethodGenerator getPlaceholderMethodGenerator,
      final AnyValueIsAvailableMethodGenerator anyValueIsAvailableMethodGenerator,
      final SpecificValueIsAvailableMethodGenerator specificValueIsAvailableGenerator,
      final CastWrapperGenerator castWrapperGenerator) {
    
    this.getDefaultMethodGenerator = checkNotNull(getDefaultMethodGenerator);
    this.getValueMethodGenerator = checkNotNull(getValueMethodGenerator);
    this.getPlaceholderMethodGenerator = checkNotNull(getPlaceholderMethodGenerator);
    this.anyValueIsAvailableGenerator = checkNotNull(anyValueIsAvailableMethodGenerator);
    this.specificValueIsAvailableGenerator = checkNotNull(specificValueIsAvailableGenerator);
    this.castWrapperGenerator = checkNotNull(castWrapperGenerator);
  }
  
  public TypeSpec generateFor(
      final ExecutableElement method,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    checkNotNull(method, "Argument \'method\' cannot be null.");
    checkNotNull(targetParameter, "Argument \'targetParameter\' cannot be null.");
    checkNotNull(contextParameter, "Argument \'contextParameter\' cannot be null.");
    checkNotNull(attrsParameter, "Argument \'attrsParameter\' cannot be null.");
    
    if (ConditionalHandlerRetriever.hasAnnotation(method)) {
      return generateForCallHandler(method, targetParameter, contextParameter, attrsParameter);
      
    } else if (UnconditionalHandlerRetriever.hasAnnotation(method)) {
      if (DefaultRetriever.hasAnnotation(method)) {
        return generateForValueHandlerWithDefault(method, targetParameter, contextParameter, attrsParameter);
      } else {
        return generateCallerForValueHandlerWithoutDefault(
            method,
            targetParameter,
            contextParameter,
            attrsParameter);
      }
    } else {
      throw new IllegalArgumentException("Argument \'method\' does not have a handler annotation.");
    }
  }
  
  private TypeSpec generateForCallHandler(
      final ExecutableElement e,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
        getNameOfTargetClass(e),
        targetParameter,
        contextParameter,
        attrsParameter);
    
    final CodeBlock.Builder invocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());
    
    for (int i = 0; i < e.getParameters().size(); i++) {
      final VariableElement parameter = e.getParameters().get(i);
      final AnnotationMirror placeholder = PlaceholderRetriever.getAnnotation(parameter);
      final MethodSpec argMethod = getPlaceholderMethodGenerator.generateFor(placeholder, i);
      
      invocationBuilder.add(castWrapperGenerator.generateFor(argMethod, parameter.asType()));
      callerBuilder.addMethod(argMethod);
      
      if (i < e.getParameters().size() - 1) {
        invocationBuilder.add(", ");
      }
    }
    
    invocationBuilder.add(");\n");
    
    final MethodSpec specificValueIsAvailable = specificValueIsAvailableGenerator.generateFor(
        ConditionalHandlerRetriever.getAnnotation(e));
    
    final MethodSpec call = CallerDef
        .getNewCallMethodPrototype()
        .addCode(CodeBlock
            .builder()
            .beginControlFlow("if ($N())", specificValueIsAvailable)
            .add(invocationBuilder.build())
            .endControlFlow()
            .build())
        .build();
    
    return callerBuilder
        .addMethod(specificValueIsAvailable)
        .addMethod(call)
        .build();
  }
  
  private TypeSpec generateCallerForValueHandlerWithoutDefault(
      final ExecutableElement e,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
        getNameOfTargetClass(e),
        targetParameter,
        contextParameter,
        attrsParameter);
    
    final CodeBlock.Builder invocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());
    
    for (int i = 0; i < e.getParameters().size(); i++) {
      final VariableElement parameter = e.getParameters().get(i);
      
      final MethodSpec argMethod = PlaceholderRetriever.hasAnnotation(parameter) ?
          getPlaceholderMethodGenerator.generateFor(PlaceholderRetriever.getAnnotation(parameter), i) :
          getValueMethodGenerator.generateFor(UnconditionalHandlerRetriever.getAnnotation(e));
      
      invocationBuilder.add(castWrapperGenerator.generateFor(argMethod, parameter.asType()));
      callerBuilder.addMethod(argMethod);
      
      if (i < e.getParameters().size() - 1) {
        invocationBuilder.add(", ");
      }
    }
    
    invocationBuilder.add(");\n");
    
    final MethodSpec specificValueIsAvailable = anyValueIsAvailableGenerator.generateFor(
        UnconditionalHandlerRetriever.getAnnotation(e));
    
    final MethodSpec call = CallerDef
        .getNewCallMethodPrototype()
        .addCode(CodeBlock
            .builder()
            .beginControlFlow("if ($N())", specificValueIsAvailable)
            .add(invocationBuilder.build())
            .endControlFlow()
            .build())
        .build();
    
    return callerBuilder
        .addMethod(specificValueIsAvailable)
        .addMethod(call)
        .build();
  }
  
  private TypeSpec generateForValueHandlerWithDefault(
      final ExecutableElement e,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
        getNameOfTargetClass(e),
        targetParameter,
        contextParameter,
        attrsParameter);
    
    final CodeBlock.Builder valueAvailableCaseInvocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());
    
    final CodeBlock.Builder valueUnavailableCaseInvocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());
    
    final AnnotationMirror valueHandlerAnno = UnconditionalHandlerRetriever.getAnnotation(e);
    final MethodSpec getValueMethod = getValueMethodGenerator.generateFor(valueHandlerAnno);
    callerBuilder.addMethod(getValueMethod);
    
    final AnnotationMirror defaultAnno = DefaultRetriever.getAnnotation(e);
    final MethodSpec getDefaultMethod = getDefaultMethodGenerator.generateFor(defaultAnno);
    callerBuilder.addMethod(getDefaultMethod);
    
    for (int i = 0; i < e.getParameters().size(); i++) {
      final VariableElement parameter = e.getParameters().get(i);
      
      if (PlaceholderRetriever.hasAnnotation(parameter)) {
        final AnnotationMirror placeholder = PlaceholderRetriever.getAnnotation(parameter);
        final MethodSpec argMethod = getPlaceholderMethodGenerator.generateFor(placeholder, i);
        
        valueAvailableCaseInvocationBuilder.add(castWrapperGenerator.generateFor(argMethod, parameter.asType()));
        valueUnavailableCaseInvocationBuilder.add(castWrapperGenerator.generateFor(argMethod, parameter.asType()));
        
        callerBuilder.addMethod(argMethod);
      } else {
        valueAvailableCaseInvocationBuilder.add(castWrapperGenerator.generateFor(
            getValueMethod,
            parameter.asType()));
        
        valueUnavailableCaseInvocationBuilder.add(castWrapperGenerator.generateFor(
            getDefaultMethod,
            parameter.asType()));
      }
      
      if (i < e.getParameters().size() - 1) {
        valueAvailableCaseInvocationBuilder.add(", ");
        valueUnavailableCaseInvocationBuilder.add(", ");
      }
    }
    
    valueAvailableCaseInvocationBuilder.add(");\n");
    valueUnavailableCaseInvocationBuilder.add(");\n");
    
    final MethodSpec valueIsAvailable = anyValueIsAvailableGenerator.generateFor(
        UnconditionalHandlerRetriever.getAnnotation(e));
    
    final MethodSpec call = CallerDef
        .getNewCallMethodPrototype()
        .addCode(CodeBlock
            .builder()
            .beginControlFlow("if ($N())", valueIsAvailable)
            .add(valueAvailableCaseInvocationBuilder.build())
            .nextControlFlow("else")
            .add(valueUnavailableCaseInvocationBuilder.build())
            .endControlFlow()
            .build())
        .build();
    
    return callerBuilder
        .addMethod(valueIsAvailable)
        .addMethod(call)
        .build();
  }
  
  private TypeName getNameOfTargetClass(final ExecutableElement method) {
    final TypeElement enclosingType = (TypeElement) method.getEnclosingElement();
    return TypeName.get(enclosingType.asType());
  }
}