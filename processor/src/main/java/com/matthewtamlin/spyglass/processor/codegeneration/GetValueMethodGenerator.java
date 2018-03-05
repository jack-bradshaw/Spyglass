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
import com.matthewtamlin.spyglass.markers.annotations.unconditionalhandlers.*;
import com.matthewtamlin.spyglass.processor.definitions.AndroidClassNames;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import java.util.Map;
import java.util.function.Function;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class GetValueMethodGenerator {
  private final Map<String, Function<AnnotationMirror, MethodSpec>> methodSpecSuppliers;
  
  private final AnnotationMirrorHelper annotationMirrorHelper;
  
  @Inject
  public GetValueMethodGenerator(final AnnotationMirrorHelper annotationMirrorHelper) {
    this.annotationMirrorHelper = checkNotNull(annotationMirrorHelper);
    
    methodSpecSuppliers = ImmutableMap
        .<String, Function<AnnotationMirror, MethodSpec>>builder()
        .put(
            BooleanHandler.class.getName(),
            booleanHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getBoolean($L, false)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(booleanHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Boolean.class)
                  .addCode(body).build();
            })
        .put(
            ColorHandler.class.getName(),
            colorHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getColor($L, 1)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(colorHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body).build();
            })
        .put(
            ColorStateListHandler.class.getName(),
            colorStateListHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getColorStateList($L)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(colorStateListHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(AndroidClassNames.COLOR_STATE_LIST)
                  .addCode(body).build();
            })
        .put(
            DimensionHandler.class.getName(),
            dimensionHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getDimension($L, Float.NEGATIVE_INFINITY)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(dimensionHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body).build();
            })
        .put(
            DrawableHandler.class.getName(),
            drawableHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getDrawable($L)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(drawableHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(AndroidClassNames.DRAWABLE)
                  .addCode(body).build();
            })
        .put(
            EnumConstantHandler.class.getName(),
            enumConstantHandlerAnnotation -> {
              final String enumClass = getLiteralFromAnnotation(enumConstantHandlerAnnotation, "enumClass");
              final String enumClassName = enumClass.substring(0, enumClass.lastIndexOf(".class"));
              
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "final int ordinal = $N().getInt($L, 1)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(enumConstantHandlerAnnotation, "attributeId"))
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
            })
        .put(
            EnumOrdinalHandler.class.getName(),
            enumOrdinalHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getInt($L, 1)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(enumOrdinalHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body).build();
            })
        .put(
            FloatHandler.class.getName(),
            floatHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getFloat($L, Float.NEGATIVE_INFINITY)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(floatHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body).build();
            })
        .put(
            FractionHandler.class.getName(),
            fractionHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(fractionHandlerAnnotation, "attributeId"),
                      getLiteralFromAnnotation(fractionHandlerAnnotation, "baseMultiplier"),
                      getLiteralFromAnnotation(fractionHandlerAnnotation, "parentMultiplier"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body).build();
            })
        .put(
            IntegerHandler.class.getName(),
            integerHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getInt($L, 1)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(integerHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body).build();
            })
        .put(
            StringHandler.class.getName(),
            stringHandlerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getString($L)",
                      CallerDef.GET_ATTRS,
                      getLiteralFromAnnotation(stringHandlerAnnotation, "attributeId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(String.class)
                  .addCode(body).build();
            })
        .build();
  }
  
  public MethodSpec generateFor(final AnnotationMirror unconditionalHandlerAnnotation) {
    checkNotNull(unconditionalHandlerAnnotation, "Argument \'unconditionalHandlerAnnotation\' cannot be null.");
    
    final String annotationClassName = unconditionalHandlerAnnotation.getAnnotationType().toString();
    
    if (!methodSpecSuppliers.containsKey(annotationClassName)) {
      throw new IllegalArgumentException("Argument \'unconditionalHandlerAnnotation\' cannot contain null.");
    }
    
    return methodSpecSuppliers.get(annotationClassName).apply(unconditionalHandlerAnnotation);
  }
  
  private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
    return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
  }
  
  private MethodSpec.Builder getBaseMethodSpec() {
    return MethodSpec.methodBuilder("getValue");
  }
}