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
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.*;
import com.matthewtamlin.spyglass.processor.definitions.AndroidClassNames;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import java.util.HashMap;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class GetValueMethodGenerator {
  private final Map<String, ParametrisedSupplier<AnnotationMirror, MethodSpec>> methodSpecSuppliers;
  
  private final AnnotationMirrorHelper annotationMirrorHelper;
  
  {
    methodSpecSuppliers = new HashMap<>();
    
    methodSpecSuppliers.put(
        BooleanHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getBoolean($L, false)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(Boolean.class)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        ColorHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getColor($L, 1)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(Number.class)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        ColorStateListHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getColorStateList($L)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(AndroidClassNames.COLOR_STATE_LIST)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        DimensionHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getDimension($L, Float.NEGATIVE_INFINITY)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(Number.class)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        DrawableHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getDrawable($L)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(AndroidClassNames.DRAWABLE)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        EnumConstantHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final String enumClass = getLiteralFromAnnotation(anno, "enumClass");
            final String enumClassName = enumClass.substring(0, enumClass.lastIndexOf(".class"));
            
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "final int ordinal = $N().getInt($L, 1)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .add("\n")
                .beginControlFlow(
                    "if (ordinal < 0 || $1T.values().length - 1 < ordinal)",
                    ClassName.bestGuess(enumClassName))
                .addStatement(
                    "throw new $T($L)",
                    RuntimeException.class,
                    "\"Ordinal \" + ordinal + \" is out of bounds for enum " + enumClassName + "\"")
                .endControlFlow()
                .add("\n")
                .addStatement(
                    "return $T.values()[ordinal]",
                    ClassName.bestGuess(enumClassName))
                .build();
            
            return getBaseMethodSpec()
                .returns(ClassName.bestGuess(enumClassName))
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        EnumOrdinalHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getInt($L, 1)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(Number.class)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        FloatHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getFloat($L, Float.NEGATIVE_INFINITY)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(Number.class)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        FractionHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"),
                    getLiteralFromAnnotation(anno, "baseMultiplier"),
                    getLiteralFromAnnotation(anno, "parentMultiplier"))
                .build();
            
            return getBaseMethodSpec()
                .returns(Number.class)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        IntegerHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getInt($L, 1)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(Number.class)
                .addCode(body).build();
          }
        }
    );
    
    methodSpecSuppliers.put(
        StringHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
          @Override
          public MethodSpec supplyFor(final AnnotationMirror anno) {
            final CodeBlock body = CodeBlock
                .builder()
                .addStatement(
                    "return $N().getString($L)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(anno, "attributeId"))
                .build();
            
            return getBaseMethodSpec()
                .returns(String.class)
                .addCode(body).build();
          }
        }
    );
  }
  
  @Inject
  public GetValueMethodGenerator(final AnnotationMirrorHelper annotationMirrorHelper) {
    this.annotationMirrorHelper = checkNotNull(annotationMirrorHelper);
  }
  
  /**
   * Creates a method spec equivalent to the following:
   * <pre>{@code
   * public Object getValue(final TypedArray attrs) {
   * 	dynamic implementation here
   * }}</pre>
   * <p>
   * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
   * queries the supplied typed array and returns a value from it. Exactly what is returned is determined by each
   * specific implementation.
   *
   * @param valueHandlerAnno
   *     the annotation to use when generating the method body, not null
   *
   * @return the method spec, not null
   *
   * @throws IllegalArgumentException
   *     if {@code valueHandlerAnno} is null
   * @throws IllegalArgumentException
   *     if {@code valueHandlerAnno} is not a value handler annotation
   */
  public MethodSpec generateFor(final AnnotationMirror valueHandlerAnno) {
    checkNotNull(valueHandlerAnno, "Argument \'valueHandlerAnno\' cannot be null.");
    
    final String annoClassName = valueHandlerAnno.getAnnotationType().toString();
    
    if (!methodSpecSuppliers.containsKey(annoClassName)) {
      throw new IllegalArgumentException("Argument \'valueHandlerAnno\' cannot contain null.");
    }
    
    return methodSpecSuppliers.get(annoClassName).supplyFor(valueHandlerAnno);
  }
  
  private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
    return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
  }
  
  private MethodSpec.Builder getBaseMethodSpec() {
    return MethodSpec.methodBuilder("getValue");
  }
}