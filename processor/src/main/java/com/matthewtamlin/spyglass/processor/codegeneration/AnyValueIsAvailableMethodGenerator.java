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
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class AnyValueIsAvailableMethodGenerator {
  private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>> methodBodySuppliers;
  
  private final AnnotationMirrorHelper annotationMirrorHelper;
  
  @Inject
  public AnyValueIsAvailableMethodGenerator(final AnnotationMirrorHelper annotationMirrorHelper) {
    this.annotationMirrorHelper = checkNotNull(annotationMirrorHelper);
    
    methodBodySuppliers = ImmutableMap
        .<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>builder()
        .put(
            BooleanHandler.class.getName(),
            booleanHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getBoolean($2L, false) == false && \n" +
                        "$1N().getBoolean($2L, true) == true",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(booleanHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            ColorHandler.class.getName(),
            colorHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getColor($2L, 1) == 1 && \n" +
                        "$1N().getColor($2L, 2) == 2",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(colorHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            ColorStateListHandler.class.getName(),
            colorStateListHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "return $N().getColorStateList($L) != null",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(colorStateListHandlerAnnotation, "attributeId"))
                .build())
        .put(
            DimensionHandler.class.getName(),
            dimensionHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement("final float negInf = Float.NEGATIVE_INFINITY")
                .addStatement("final float posInf = Float.POSITIVE_INFINITY")
                .add("\n")
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getDimension($2L, negInf) == negInf && \n" +
                        "$1N().getDimension($2L, posInf) == posInf",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(dimensionHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            DrawableHandler.class.getName(),
            drawableHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "return $N().getDrawable($L) != null",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(drawableHandlerAnnotation, "attributeId"))
                .build())
        .put(
            EnumConstantHandler.class.getName(),
            enumConstantHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getInt($2L, 1) == 1 && \n" +
                        "$1N().getInt($2L, 2) == 2",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(enumConstantHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            EnumOrdinalHandler.class.getName(),
            enumOrdinalHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getInt($2L, 1) == 1 && \n" +
                        "$1N().getInt($2L, 2) == 2",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(enumOrdinalHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            FloatHandler.class.getName(),
            floatHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement("final float negInf = Float.NEGATIVE_INFINITY")
                .addStatement("final float posInf = Float.POSITIVE_INFINITY")
                .add("\n")
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getFloat($2L, negInf) == negInf && \n" +
                        "$1N().getFloat($2L, posInf) == posInf",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(floatHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            FractionHandler.class.getName(),
            fractionHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement("final float negInf = Float.NEGATIVE_INFINITY")
                .addStatement("final float posInf = Float.POSITIVE_INFINITY")
                .add("\n")
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getFraction($2L, 1, 1, negInf) == negInf && \n" +
                        "$1N().getFraction($2L, 1, 1, posInf) == posInf",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(fractionHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            IntegerHandler.class.getName(),
            integerHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getInt($2L, 1) == 1 && \n" +
                        "$1N().getInt($2L, 2) == 2",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(integerHandlerAnnotation, "attributeId"))
                .add("\n")
                .addStatement("return !defaultConsistentlyReturned")
                .build())
        .put(
            StringHandler.class.getName(),
            stringHandlerAnnotation -> CodeBlock
                .builder()
                .addStatement(
                    "return $N().hasValue($L)",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(stringHandlerAnnotation, "attributeId"))
                .build())
        .build();
  }
  
  public MethodSpec generateFor(final AnnotationMirror unconditionalHandlerAnnotation) {
    checkNotNull(unconditionalHandlerAnnotation, "Argument \'unconditionalHandlerAnnotation\' cannot be null.");
    
    final String annotationClassName = unconditionalHandlerAnnotation.getAnnotationType().toString();
    
    if (!methodBodySuppliers.containsKey(annotationClassName)) {
      throw new IllegalArgumentException("Argument \'unconditionalHandlerAnnotation\' is not an unconditional handler.");
    }
    
    return MethodSpec
        .methodBuilder("valueIsAvailable")
        .returns(Boolean.class)
        .addCode(methodBodySuppliers.get(annotationClassName).supplyFor(unconditionalHandlerAnnotation))
        .build();
  }
  
  private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
    return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
  }
}