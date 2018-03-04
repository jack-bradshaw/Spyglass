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

import com.google.common.collect.ImmutableMap;
import com.matthewtamlin.spyglass.markers.annotations.placeholders.*;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.IntChecker.checkGreaterThanOrEqualTo;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class GetPlaceholderMethodGenerator {
  private final Map<String, MethodSpecSupplier> methodSpecSuppliers;
  
  private AnnotationMirrorHelper annotationMirrorHelper;
  
  @Inject
  public GetPlaceholderMethodGenerator(final AnnotationMirrorHelper annotationMirrorHelper) {
    this.annotationMirrorHelper = checkNotNull(annotationMirrorHelper);
    
    methodSpecSuppliers = ImmutableMap
        .<String, MethodSpecSupplier>builder()
        .put(UseByte.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier())
        .put(UseDouble.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier())
        .put(UseFloat.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier())
        .put(UseInt.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier())
        .put(UseLong.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier())
        .put(UseShort.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier())
        .put(
            UseBoolean.class.getCanonicalName(),
            (useBooleanAnnotation, position) -> {
              final AnnotationValue rawValue = annotationMirrorHelper.getValueUsingDefaults(
                  useBooleanAnnotation,
                  "value");
              
              return getBaseMethodSpec(position)
                  .returns(Boolean.class)
                  .addCode(CodeBlock
                      .builder()
                      .addStatement("return $L", rawValue.toString())
                      .build())
                  .build();
            })
        .put(
            UseChar.class.getCanonicalName(),
            (useCharAnnotation, position) -> {
              final AnnotationValue rawValue = annotationMirrorHelper.getValueUsingDefaults(useCharAnnotation, "value");
              
              return getBaseMethodSpec(position)
                  .returns(Character.class)
                  .addCode(CodeBlock
                      .builder()
                      .addStatement("return ($T) $L", Character.class, rawValue.toString())
                      .build())
                  .build();
            })
        .put(
            UseNull.class.getCanonicalName(),
            (useNullAnnotation, position) -> getBaseMethodSpec(position)
                .returns(Object.class)
                .addCode(CodeBlock
                    .builder()
                    .addStatement("return null")
                    .build())
                .build())
        .put(
            UseString.class.getCanonicalName(),
            (useStringAnnotation, position) -> {
              final AnnotationValue rawValue = annotationMirrorHelper.getValueUsingDefaults(
                  useStringAnnotation,
                  "value");
              
              return getBaseMethodSpec(position)
                  .returns(String.class)
                  .addCode(CodeBlock
                      .builder()
                      .addStatement("return ($T) $L", String.class, rawValue.toString())
                      .build())
                  .build();
            })
        .build();
  }
  
  public MethodSpec generateFor(final AnnotationMirror placeholderAnnotation, final int parameterIndex) {
    checkNotNull(placeholderAnnotation, "Argument \'placeholderAnnotation\' cannot be null.");
    checkGreaterThanOrEqualTo(parameterIndex, 0, "Argument \'parameterIndex\' must be at least zero.");
    
    final String annotationClassName = placeholderAnnotation.getAnnotationType().toString();
    
    if (!methodSpecSuppliers.containsKey(annotationClassName)) {
      throw new IllegalArgumentException("Argument \'placeholderAnnotation\' is not a placeholder annotation.");
    }
    
    return methodSpecSuppliers.get(annotationClassName).supplyFor(placeholderAnnotation, parameterIndex);
  }
  
  private MethodSpec.Builder getBaseMethodSpec(final int position) {
    return MethodSpec.methodBuilder("getArgument" + position);
  }
  
  private interface MethodSpecSupplier {
    public MethodSpec supplyFor(AnnotationMirror placeholder, int position);
  }
  
  private class PrimitiveToNumberMethodSpecSupplier implements MethodSpecSupplier {
    @Override
    public MethodSpec supplyFor(final AnnotationMirror placeholder, final int position) {
      final AnnotationValue rawValue = annotationMirrorHelper.getValueUsingDefaults(placeholder, "value");
      
      return getBaseMethodSpec(position)
          .returns(Number.class)
          .addCode(CodeBlock
              .builder()
              .addStatement("return $L", rawValue.toString())
              .build())
          .build();
    }
  }
}