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

package com.matthewtamlin.spyglass.processor.validation;

import com.google.common.collect.ImmutableList;
import com.matthewtamlin.spyglass.markers.annotations.defaults.DefaultToNull;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.UseNull;
import com.matthewtamlin.spyglass.processor.annotationretrievers.DefaultRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.PlaceholderRetriever;
import com.matthewtamlin.spyglass.processor.annotationretrievers.UnconditionalHandlerRetriever;
import com.matthewtamlin.spyglass.processor.codegeneration.GetDefaultMethodGenerator;
import com.matthewtamlin.spyglass.processor.codegeneration.GetPlaceholderMethodGenerator;
import com.matthewtamlin.spyglass.processor.codegeneration.GetValueMethodGenerator;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.TypeMirrorHelper;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class TypeValidator implements Validator {
  private final Elements elementHelper;
  
  private final Types typeHelper;
  
  private final TypeMirrorHelper typeMirrorHelper;
  
  private final List<Rule> rules;
  
  @Inject
  public TypeValidator(
      final Elements elementUtil,
      final Types typeUtil,
      final TypeMirrorHelper typeMirrorHelper,
      final GetValueMethodGenerator getValueMethodGenerator,
      final GetDefaultMethodGenerator getDefaultMethodGenerator,
      final GetPlaceholderMethodGenerator getPlaceholderMethodGenerator) {
    
    this.elementHelper = checkNotNull(elementUtil);
    this.typeHelper = checkNotNull(typeUtil);
    this.typeMirrorHelper = checkNotNull(typeMirrorHelper);
    
    checkNotNull(getValueMethodGenerator);
    checkNotNull(getDefaultMethodGenerator);
    checkNotNull(getPlaceholderMethodGenerator);
    
    rules = ImmutableList.of(
        element -> {
          if (!UnconditionalHandlerRetriever.hasAnnotation(element)) {
            return Result.createSuccessful();
          }
          
          final AnnotationMirror annotation = UnconditionalHandlerRetriever.getAnnotation(element);
          
          final MethodSpec supplier = getValueMethodGenerator.generateFor(annotation);
          final TypeMirror suppliedType = returnTypeToTypeMirror(supplier);
          final TypeMirror recipientType = getParameterWithoutPlaceholderAnnotation(element).asType();
          
          if (!isAssignableOrConvertible(suppliedType, recipientType)) {
            return Result.createFailure(
                "Misused handler annotation found. \'%1$s\' cannot be cast to \'%2$s\'.",
                suppliedType,
                recipientType);
          }
          
          return Result.createSuccessful();
        },
        
        element -> {
          if (!DefaultRetriever.hasAnnotation(element)) {
            return Result.createSuccessful();
          }
          
          final AnnotationMirror annotation = DefaultRetriever.getAnnotation(element);
          final String annotationName = annotation.getAnnotationType().toString();
          
          final MethodSpec supplier = getDefaultMethodGenerator.generateFor(annotation);
          final TypeMirror suppliedType = returnTypeToTypeMirror(supplier);
          final TypeMirror recipientType = getParameterWithoutPlaceholderAnnotation(element).asType();
          
          if (annotationName.equals(DefaultToNull.class.getName())) {
            if (typeMirrorHelper.isPrimitive(recipientType)) {
              return Result.createFailure(
                  "Misused default annotation found. Primitive parameters cannot receive null.");
              
            } else {
              return Result.createSuccessful();
            }
          }
          
          if (!isAssignableOrConvertible(suppliedType, recipientType)) {
            return Result.createFailure(
                "Misused default annotation found. \'%1$s\' cannot be cast to \'%2$s\'.",
                suppliedType,
                recipientType);
          }
          
          return Result.createSuccessful();
        },
        
        new Rule() {
          @Override
          public Result checkElement(final ExecutableElement element) {
            for (final VariableElement parameter : element.getParameters()) {
              if (PlaceholderRetriever.hasAnnotation(parameter)) {
                final Result result = checkParameter(parameter);
                
                if (!result.isSuccessful()) {
                  return result;
                }
              }
            }
            
            return Result.createSuccessful();
          }
          
          private Result checkParameter(final VariableElement parameter) {
            final AnnotationMirror annotation = PlaceholderRetriever.getAnnotation(parameter);
            final String annotationName = annotation.getAnnotationType().toString();
            
            final MethodSpec supplier = getPlaceholderMethodGenerator.generateFor(annotation, 0);
            final TypeMirror suppliedType = returnTypeToTypeMirror(supplier);
            final TypeMirror recipientType = parameter.asType();
            
            if (annotationName.equals(UseNull.class.getName())) {
              if (typeMirrorHelper.isPrimitive(recipientType)) {
                return Result.createFailure(
                    "Misused placeholder annotation found. Primitive parameters cannot receive null.");
                
              } else {
                return Result.createSuccessful();
              }
            }
            
            if (!isAssignableOrConvertible(suppliedType, recipientType)) {
              return Result.createFailure(
                  "Misused placeholder annotation found. \'%1$s\' cannot be cast to \'%2$s\'.",
                  suppliedType,
                  recipientType);
            }
            
            return Result.createSuccessful();
          }
        });
  }
  
  public Result validate(final ExecutableElement element) {
    for (final Rule rule : rules) {
      final Result result = rule.checkElement(element);
      
      if (!result.isSuccessful()) {
        return result;
      }
    }
    
    return Result.createSuccessful();
  }
  
  private TypeMirror returnTypeToTypeMirror(final MethodSpec methodSpec) {
    return elementHelper.getTypeElement(methodSpec.returnType.toString()).asType();
  }
  
  private static VariableElement getParameterWithoutPlaceholderAnnotation(final ExecutableElement method) {
    for (final VariableElement parameter : method.getParameters()) {
      if (!PlaceholderRetriever.hasAnnotation(parameter)) {
        return parameter;
      }
    }
    
    return null;
  }
  
  private boolean isAssignableOrConvertible(final TypeMirror suppliedType, final TypeMirror recipientType) {
    return typeHelper.isAssignable(suppliedType, recipientType) ||
        (typeMirrorHelper.isNumber(suppliedType) && typeMirrorHelper.isNumber(recipientType)) ||
        (typeMirrorHelper.isCharacter(suppliedType) && typeMirrorHelper.isCharacter(recipientType));
  }
  
  private interface Rule {
    public Result checkElement(ExecutableElement element);
  }
}