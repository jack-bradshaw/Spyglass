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

import com.matthewtamlin.spyglass.markers.annotations.placeholders.*;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import java.util.HashMap;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.IntChecker.checkGreaterThanOrEqualTo;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class GetPlaceholderMethodGenerator {
  private final Map<String, MethodSpecSupplier> methodSpecSuppliers = new HashMap<>();
  
  private AnnotationMirrorHelper annoMirrorHelper;
  
  {
    methodSpecSuppliers.put(UseByte.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
    methodSpecSuppliers.put(UseDouble.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
    methodSpecSuppliers.put(UseFloat.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
    methodSpecSuppliers.put(UseInt.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
    methodSpecSuppliers.put(UseLong.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
    methodSpecSuppliers.put(UseShort.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
    
    methodSpecSuppliers.put(
        UseBoolean.class.getCanonicalName(),
        new MethodSpecSupplier() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
            final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");
            
            return getBaseMethodSpec(position)
                .returns(Boolean.class)
                .addCode(CodeBlock
                    .builder()
                    .addStatement("return $L", rawValue.toString())
                    .build())
                .build();
          }
        });
    methodSpecSuppliers.put(
        UseChar.class.getCanonicalName(),
        new MethodSpecSupplier() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
            final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");
            
            return getBaseMethodSpec(position)
                .returns(Character.class)
                .addCode(CodeBlock
                    .builder()
                    .addStatement("return ($T) $L", Character.class, rawValue.toString())
                    .build())
                .build();
          }
        });
    
    
    methodSpecSuppliers.put(
        UseNull.class.getCanonicalName(),
        new MethodSpecSupplier() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
            return getBaseMethodSpec(position)
                .returns(Object.class)
                .addCode(CodeBlock
                    .builder()
                    .addStatement("return null")
                    .build())
                .build();
          }
        });
    
    methodSpecSuppliers.put(
        UseString.class.getCanonicalName(),
        new MethodSpecSupplier() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
            final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");
            
            return getBaseMethodSpec(position)
                .returns(String.class)
                .addCode(CodeBlock
                    .builder()
                    .addStatement("return ($T) $L", String.class, rawValue.toString())
                    .build())
                .build();
          }
        }
    );
  }
  
  @Inject
  public GetPlaceholderMethodGenerator(final AnnotationMirrorHelper annotationMirrorHelper) {
    this.annoMirrorHelper = checkNotNull(annotationMirrorHelper);
  }
  
  public MethodSpec generateFor(final AnnotationMirror useAnno, final int parameterIndex) {
    checkNotNull(useAnno, "Argument \'useAnno\' cannot be null.");
    checkGreaterThanOrEqualTo(parameterIndex, 0, "Argument \'parameterIndex\' must be at least zero.");
    
    final String annoClassName = useAnno.getAnnotationType().toString();
    
    if (!methodSpecSuppliers.containsKey(annoClassName)) {
      throw new IllegalArgumentException("Argument \'useAnno\' is not a use-annotation.");
    }
    
    return methodSpecSuppliers.get(annoClassName).supplyFor(useAnno, parameterIndex);
  }
  
  private MethodSpec.Builder getBaseMethodSpec(final int position) {
    return MethodSpec.methodBuilder("getArgument" + position);
  }
  
  private interface MethodSpecSupplier {
    public MethodSpec supplyFor(AnnotationMirror useAnno, int position);
  }
  
  private class PrimitiveToNumberMethodSpecSupplier implements MethodSpecSupplier {
    @Override
    public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
      final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");
      
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