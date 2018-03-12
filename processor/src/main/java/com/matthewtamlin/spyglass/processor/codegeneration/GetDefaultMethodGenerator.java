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
import com.matthewtamlin.spyglass.markers.annotations.defaults.*;
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

public class GetDefaultMethodGenerator {
  private final Map<String, Function<AnnotationMirror, MethodSpec>> methodSpecSuppliers;
  
  private final AnnotationMirrorHelper annotationMirrorHelper;
  
  @Inject
  public GetDefaultMethodGenerator(final AnnotationMirrorHelper annotationMirrorHelper) {
    this.annotationMirrorHelper = checkNotNull(annotationMirrorHelper);
    
    methodSpecSuppliers = ImmutableMap
        .<String, Function<AnnotationMirror, MethodSpec>>builder()
        .put(
            DefaultToBoolean.class.getName(),
            defaultToBooleanAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement("return $L", getLiteralFromAnnotation(defaultToBooleanAnnotation, "value"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Boolean.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToBooleanResource.class.getName(),
            defaultToBooleanResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getResources().getBoolean($L)",
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToBooleanResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Boolean.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToColorResource.class.getName(),
            defaultToColorResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $T.getColor($N(), $L)",
                      AndroidClassNames.CONTEXT_COMPAT,
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToColorResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToColorStateListResource.class.getName(),
            defaultToColorStateListResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $T.getColorStateList($N(), $L)",
                      AndroidClassNames.APP_COMPAT_RESOURCES,
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToColorStateListResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(AndroidClassNames.COLOR_STATE_LIST)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToDimension.class.getName(),
            defaultToDimensionAnnotation -> {
              final String unitFull = getLiteralFromAnnotation(defaultToDimensionAnnotation, "unit");
              final String unitShort = unitFull.substring(unitFull.lastIndexOf(".") + 1);
              
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "$T metrics = $N().getResources().getDisplayMetrics()",
                      AndroidClassNames.DISPLAY_METRICS,
                      CallerDef.GET_CONTEXT)
                  .addStatement(
                      "return $1T.applyDimension($1T.$2L, $3L, metrics)",
                      AndroidClassNames.TYPED_VALUE,
                      getComplexUnitLiteral(unitShort),
                      getLiteralFromAnnotation(defaultToDimensionAnnotation, "value"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToDimensionResource.class.getName(),
            defaultToDimensionResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getResources().getDimension($L)",
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToDimensionResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToDrawableResource.class.getName(),
            defaultToDrawableResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $T.getDrawable($N(), $L)",
                      AndroidClassNames.APP_COMPAT_RESOURCES,
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToDrawableResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(AndroidClassNames.DRAWABLE)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToEnumConstant.class.getName(),
            defaultToEnumConstantAnnotation -> {
              final String enumClass = getLiteralFromAnnotation(defaultToEnumConstantAnnotation, "enumClass");
              final String enumClassName = enumClass.substring(0, enumClass.lastIndexOf(".class"));
              
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "final int ordinal = $L",
                      getLiteralFromAnnotation(defaultToEnumConstantAnnotation,
                          "ordinal"))
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
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToFloat.class.getName(),
            defaultToFloatAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement("return $L", getLiteralFromAnnotation(defaultToFloatAnnotation, "value"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToFractionResource.class.getName(),
            defaultToFractionResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getResources().getFraction($L, $L, $L)",
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToFractionResourceAnnotation, "resId"),
                      getLiteralFromAnnotation(defaultToFractionResourceAnnotation, "baseMultiplier"),
                      getLiteralFromAnnotation(defaultToFractionResourceAnnotation, "parentMultiplier"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToInteger.class.getName(),
            defaultToIntegerAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement("return $L", getLiteralFromAnnotation(defaultToIntegerAnnotation, "value"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToIntegerResource.class.getName(),
            defaultToIntegerResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getResources().getInteger($L)",
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToIntegerResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Number.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToNull.class.getName(),
            defaultToNullAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement("return null")
                  .build();
              
              return getBaseMethodSpec()
                  .returns(Object.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToString.class.getName(),
            defaultToStringAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement("return $L", getLiteralFromAnnotation(defaultToStringAnnotation, "value"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(String.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToStringResource.class.getName(),
            defaultToStringResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getResources().getString($L)",
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToStringResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(String.class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToTextArrayResource.class.getName(),
            defaultToTextArrayResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getResources().getTextArray($L)",
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToTextArrayResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(CharSequence[].class)
                  .addCode(body)
                  .build();
            })
        .put(
            DefaultToTextResource.class.getName(),
            defaultToTextResourceAnnotation -> {
              final CodeBlock body = CodeBlock
                  .builder()
                  .addStatement(
                      "return $N().getResources().getText($L)",
                      CallerDef.GET_CONTEXT,
                      getLiteralFromAnnotation(defaultToTextResourceAnnotation, "resId"))
                  .build();
              
              return getBaseMethodSpec()
                  .returns(CharSequence.class)
                  .addCode(body)
                  .build();
            })
        .build();
  }
  
  public MethodSpec generateFor(final AnnotationMirror defaultAnnotation) {
    checkNotNull(defaultAnnotation, "Argument \'defaultAnnotation\' cannot be null.");
    
    final String annotationClassName = defaultAnnotation.getAnnotationType().toString();
    
    if (!methodSpecSuppliers.containsKey(annotationClassName)) {
      throw new IllegalArgumentException("Argument \'defaultAnnotation\' is not a default annotation.");
    }
    
    return methodSpecSuppliers.get(annotationClassName).apply(defaultAnnotation);
  }
  
  private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
    return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
  }
  
  private String getComplexUnitLiteral(final String unit) {
    switch (unit) {
      case "PX":
        return "COMPLEX_UNIT_PX";
      case "DP":
        return "COMPLEX_UNIT_DIP";
      case "PT":
        return "COMPLEX_UNIT_PT";
      case "IN":
        return "COMPLEX_UNIT_IN";
      case "SP":
        return "COMPLEX_UNIT_SP";
      case "MM":
        return "COMPLEX_UNIT_MM";
      default:
        throw new IllegalStateException("Unexpected unit: " + unit);
    }
  }
  
  private MethodSpec.Builder getBaseMethodSpec() {
    return MethodSpec.methodBuilder("getDefault");
  }
}