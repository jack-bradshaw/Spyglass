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

import com.matthewtamlin.spyglass.processor.annotationretrievers.ConditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.DefaultRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.PlaceholderRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.UnconditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.definitions.RxJavaClassNames;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.TypeMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class CallerGenerator {
  private final Elements elementUtil;
  
  private final Types typeUtil;
  
  private final TypeMirrorHelper typeMirrorHelper;
  
  private final GetDefaultMethodGenerator getDefaultMethodGenerator;
  
  private final GetValueMethodGenerator getValueMethodGenerator;
  
  private final GetPlaceholderMethodGenerator getPlaceholderMethodGenerator;
  
  private final AnyValueIsAvailableMethodGenerator anyValueIsAvailableGenerator;
  
  private final SpecificValueIsAvailableMethodGenerator specificValueIsAvailableGenerator;
  
  private final CastWrapperGenerator castWrapperGenerator;
  
  @Inject
  public CallerGenerator(
      final Elements elementUtil,
      final Types typeUtil,
      final TypeMirrorHelper typeMirrorHelper,
      final GetDefaultMethodGenerator getDefaultMethodGenerator,
      final GetValueMethodGenerator getValueMethodGenerator,
      final GetPlaceholderMethodGenerator getPlaceholderMethodGenerator,
      final AnyValueIsAvailableMethodGenerator anyValueIsAvailableMethodGenerator,
      final SpecificValueIsAvailableMethodGenerator specificValueIsAvailableGenerator,
      final CastWrapperGenerator castWrapperGenerator) {
    
    this.elementUtil = checkNotNull(elementUtil);
    this.typeUtil = checkNotNull(typeUtil);
    this.typeMirrorHelper = checkNotNull(typeMirrorHelper);
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
      return generateForConditionalHandler(method, targetParameter, contextParameter, attrsParameter);
      
    } else if (UnconditionalHandlerRetriever.hasAnnotation(method)) {
      if (DefaultRetriever.hasAnnotation(method)) {
        return generateForUnconditionalHandlerWithDefault(method, targetParameter, contextParameter, attrsParameter);
        
      } else {
        return generateForUnconditionalHandlerWithoutDefault(
            method,
            targetParameter,
            contextParameter,
            attrsParameter);
      }
    } else {
      throw new IllegalArgumentException("Argument \'method\' does not have a handler annotation.");
    }
  }
  
  private TypeSpec generateForConditionalHandler(
      final ExecutableElement method,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
        getNameOfTargetClass(method),
        targetParameter,
        contextParameter,
        attrsParameter);
    
    final CodeBlock.Builder invocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, method.getSimpleName());
    
    for (int i = 0; i < method.getParameters().size(); i++) {
      final VariableElement parameter = method.getParameters().get(i);
      final AnnotationMirror placeholder = PlaceholderRetriever.getAnnotation(parameter);
      final MethodSpec argMethod = getPlaceholderMethodGenerator.generateFor(placeholder, i);
      
      invocationBuilder.add(castWrapperGenerator.generateFor(argMethod, parameter.asType()));
      
      callerBuilder.addMethod(argMethod);
      
      if (i < method.getParameters().size() - 1) {
        invocationBuilder.add(", ");
      }
    }
    
    invocationBuilder.add(")");
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      if (isSingle(method.getReturnType())) {
        invocationBuilder.add(".toCompletable()");
        
      } else if (isMaybe(method.getReturnType())) {
        invocationBuilder.add(".ignoreElement()");
        
      } else if (!isCompletable(method.getReturnType())) {
        invocationBuilder.add(".ignoreElements()");
      }
    }
    
    invocationBuilder.add(";\n");
    
    final MethodSpec specificValueIsAvailable = specificValueIsAvailableGenerator.generateFor(
        ConditionalHandlerRetriever.getAnnotation(method));
    
    final CodeBlock.Builder callMethodCodeBlockBuilder = CodeBlock.builder();
    
    if (!typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.beginControlFlow("return $T.fromRunnable(() -> ", RxJavaClassNames.COMPLETABLE);
    }
    
    callMethodCodeBlockBuilder
        .beginControlFlow("try")
        .beginControlFlow("if ($N())", specificValueIsAvailable);
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.add("return ");
    }
    
    callMethodCodeBlockBuilder.add(invocationBuilder.build());
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder
          .nextControlFlow("else")
          .addStatement("return $T.complete()", RxJavaClassNames.COMPLETABLE);
    }
    
    callMethodCodeBlockBuilder
        .endControlFlow()
        .nextControlFlow("catch (final Throwable error)");
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.addStatement(
          "return $T.error(new $T($S, error))",
          RxJavaClassNames.COMPLETABLE,
          RuntimeException.class,
          "The Spyglass Framework encountered an exception when calling a target method.");
      
    } else {
      callMethodCodeBlockBuilder.addStatement(
          "throw new $T($S, error)",
          RuntimeException.class,
          "The Spyglass Framework encountered an exception when calling a target method.");
    }
    
    callMethodCodeBlockBuilder.endControlFlow();
    
    if (!typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder
          .endControlFlow()
          .addStatement(")");
    }
    
    return callerBuilder
        .addMethod(specificValueIsAvailable)
        .addMethod(CallerDef
            .getNewCallMethodPrototype()
            .addCode(callMethodCodeBlockBuilder.build())
            .build())
        .build();
  }
  
  private TypeSpec generateForUnconditionalHandlerWithoutDefault(
      final ExecutableElement method,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
        getNameOfTargetClass(method),
        targetParameter,
        contextParameter,
        attrsParameter);
    
    final CodeBlock.Builder invocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, method.getSimpleName());
    
    for (int i = 0; i < method.getParameters().size(); i++) {
      final VariableElement parameter = method.getParameters().get(i);
      
      final MethodSpec argMethod = PlaceholderRetriever.hasAnnotation(parameter) ?
          getPlaceholderMethodGenerator.generateFor(PlaceholderRetriever.getAnnotation(parameter), i) :
          getValueMethodGenerator.generateFor(UnconditionalHandlerRetriever.getAnnotation(method));
      
      invocationBuilder.add(castWrapperGenerator.generateFor(argMethod, parameter.asType()));
      
      callerBuilder.addMethod(argMethod);
      
      if (i < method.getParameters().size() - 1) {
        invocationBuilder.add(", ");
      }
    }
    
    invocationBuilder.add(")");
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      if (isSingle(method.getReturnType())) {
        invocationBuilder.add(".toCompletable()");
        
      } else if (isMaybe(method.getReturnType())) {
        invocationBuilder.add(".ignoreElement()");
        
      } else if (!isCompletable(method.getReturnType())) {
        invocationBuilder.add(".ignoreElements()");
      }
    }
    
    invocationBuilder.add(";\n");
    
    final MethodSpec anyValueIsAvailable = anyValueIsAvailableGenerator.generateFor(
        UnconditionalHandlerRetriever.getAnnotation(method));
    
    final CodeBlock.Builder callMethodCodeBlockBuilder = CodeBlock.builder();
    
    if (!typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.beginControlFlow("return $T.fromRunnable(() -> ", RxJavaClassNames.COMPLETABLE);
    }
    
    callMethodCodeBlockBuilder
        .beginControlFlow("try")
        .beginControlFlow("if ($N())", anyValueIsAvailable);
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.add("return ");
    }
    
    callMethodCodeBlockBuilder.add(invocationBuilder.build());
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder
          .nextControlFlow("else")
          .addStatement("return $T.complete()", RxJavaClassNames.COMPLETABLE);
    }
    
    callMethodCodeBlockBuilder
        .endControlFlow()
        .nextControlFlow("catch (final Throwable error)");
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.addStatement(
          "return $T.error(new $T($S, error))",
          RxJavaClassNames.COMPLETABLE,
          RuntimeException.class,
          "The Spyglass Framework encountered an exception when calling a target method.");
      
    } else {
      callMethodCodeBlockBuilder.addStatement(
          "throw new $T($S, error)",
          RuntimeException.class,
          "The Spyglass Framework encountered an exception when calling a target method.");
    }
    
    callMethodCodeBlockBuilder.endControlFlow();
    
    if (!typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder
          .endControlFlow()
          .addStatement(")");
    }
    
    return callerBuilder
        .addMethod(anyValueIsAvailable)
        .addMethod(CallerDef
            .getNewCallMethodPrototype()
            .addCode(callMethodCodeBlockBuilder.build())
            .build())
        .build();
  }
  
  private TypeSpec generateForUnconditionalHandlerWithDefault(
      final ExecutableElement method,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
        getNameOfTargetClass(method),
        targetParameter,
        contextParameter,
        attrsParameter);
    
    final CodeBlock.Builder valueAvailableCaseInvocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, method.getSimpleName());
    
    final CodeBlock.Builder valueUnavailableCaseInvocationBuilder = CodeBlock
        .builder()
        .add("$N().$N(", CallerDef.GET_TARGET, method.getSimpleName());
    
    final AnnotationMirror unconditionalHandlerAnnotation = UnconditionalHandlerRetriever.getAnnotation(method);
    final MethodSpec getValueMethod = getValueMethodGenerator.generateFor(unconditionalHandlerAnnotation);
    callerBuilder.addMethod(getValueMethod);
    
    final AnnotationMirror defaultAnnotation = DefaultRetriever.getAnnotation(method);
    final MethodSpec getDefaultMethod = getDefaultMethodGenerator.generateFor(defaultAnnotation);
    callerBuilder.addMethod(getDefaultMethod);
    
    for (int i = 0; i < method.getParameters().size(); i++) {
      final VariableElement parameter = method.getParameters().get(i);
      
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
      
      if (i < method.getParameters().size() - 1) {
        valueAvailableCaseInvocationBuilder.add(", ");
        valueUnavailableCaseInvocationBuilder.add(", ");
      }
    }
    
    valueAvailableCaseInvocationBuilder.add(")");
    valueUnavailableCaseInvocationBuilder.add(")");
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      if (isSingle(method.getReturnType())) {
        valueAvailableCaseInvocationBuilder.add(".toCompletable()");
        valueUnavailableCaseInvocationBuilder.add(".toCompletable()");
        
      } else if (isMaybe(method.getReturnType())) {
        valueAvailableCaseInvocationBuilder.add(".ignoreElement()");
        valueUnavailableCaseInvocationBuilder.add(".ignoreElement()");
        
      } else if (!isCompletable(method.getReturnType())) {
        valueAvailableCaseInvocationBuilder.add(".ignoreElements()");
        valueUnavailableCaseInvocationBuilder.add(".ignoreElements()");
      }
    }
    
    valueAvailableCaseInvocationBuilder.add(";\n");
    valueUnavailableCaseInvocationBuilder.add(";\n");
    
    final MethodSpec anyValueIsAvailable = anyValueIsAvailableGenerator.generateFor(
        UnconditionalHandlerRetriever.getAnnotation(method));
    
    final CodeBlock.Builder callMethodCodeBlockBuilder = CodeBlock.builder();
    
    if (!typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.beginControlFlow("return $T.fromRunnable(() -> ", RxJavaClassNames.COMPLETABLE);
    }
    
    callMethodCodeBlockBuilder
        .beginControlFlow("try")
        .beginControlFlow("if ($N())", anyValueIsAvailable);
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.add("return ");
    }
    
    callMethodCodeBlockBuilder
        .add(valueAvailableCaseInvocationBuilder.build())
        .nextControlFlow("else");
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.add("return ");
    }
    
    callMethodCodeBlockBuilder
        .add(valueUnavailableCaseInvocationBuilder.build())
        .endControlFlow();
    
    callMethodCodeBlockBuilder.nextControlFlow("catch (final Throwable error)");
    
    if (typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder.addStatement(
          "return $T.error(new $T($S, error))",
          RxJavaClassNames.COMPLETABLE,
          RuntimeException.class,
          "The Spyglass Framework encountered an exception when calling a target method.");
      
    } else {
      callMethodCodeBlockBuilder.addStatement(
          "throw new $T($S, error)",
          RuntimeException.class,
          "The Spyglass Framework encountered an exception when calling a target method.");
    }
    
    callMethodCodeBlockBuilder.endControlFlow();
    
    if (!typeMirrorHelper.isRxObservableType(method.getReturnType())) {
      callMethodCodeBlockBuilder
          .endControlFlow()
          .addStatement(")");
    }
    
    return callerBuilder
        .addMethod(anyValueIsAvailable)
        .addMethod(CallerDef
            .getNewCallMethodPrototype()
            .addCode(callMethodCodeBlockBuilder.build())
            .build())
        .build();
  }
  
  private TypeName getNameOfTargetClass(final ExecutableElement method) {
    final TypeElement enclosingType = (TypeElement) method.getEnclosingElement();
    
    return TypeName.get(enclosingType.asType());
  }
  
  private boolean isSingle(final TypeMirror typeMirror) {
    return typeUtil.isAssignable(
        typeMirror,
        elementUtil.getTypeElement(RxJavaClassNames.SINGLE.toString()).asType());
  }
  
  private boolean isCompletable(final TypeMirror typeMirror) {
    return typeUtil.isAssignable(
        typeMirror,
        elementUtil.getTypeElement(RxJavaClassNames.COMPLETABLE.toString()).asType());
  }
  
  private boolean isMaybe(final TypeMirror typeMirror) {
    return typeUtil.isAssignable(
        typeMirror,
        elementUtil.getTypeElement(RxJavaClassNames.MAYBE.toString()).asType());
  }
}