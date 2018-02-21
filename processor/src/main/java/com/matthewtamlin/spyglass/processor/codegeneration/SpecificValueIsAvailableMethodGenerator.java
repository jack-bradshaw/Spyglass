/*
 * Copyright 2017 Matthew David Tamlin
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
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificBooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.conditionalhandlers.SpecificFlagHandler;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import javax.lang.model.element.AnnotationMirror;
import java.util.HashMap;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class SpecificValueIsAvailableMethodGenerator {
  private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>> methodBodySuppliers;
  
  private final AnnotationMirrorHelper annotationMirrorHelper;
  
  {
    methodBodySuppliers = new HashMap<>();
    
    methodBodySuppliers.put(
        SpecificEnumHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
          @Override
          public CodeBlock supplyFor(final AnnotationMirror object) {
            return CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getInt($2L, 1) == 1 && \n" +
                        "$1N().getInt($2L, 2) == 2",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(object, "attributeId"))
                .add("\n")
                .addStatement(
                    "return defaultConsistentlyReturned ? \n" +
                        "false :\n" +
                        "$N().getInt($L, 0) == $L",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(object, "attributeId"),
                    getLiteralFromAnnotation(object, "handledOrdinal"))
                .build();
          }
        }
    );
    
    methodBodySuppliers.put(
        SpecificFlagHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
          @Override
          public CodeBlock supplyFor(final AnnotationMirror object) {
            return CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getInt($2L, 1) == 1 && \n" +
                        "$1N().getInt($2L, 2) == 2",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(object, "attributeId"))
                .add("\n")
                .addStatement(
                    "return defaultConsistentlyReturned ? \n" +
                        "false : \n" +
                        "($N().getInt($L, 0) & $L) > 0",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(object, "attributeId"),
                    getLiteralFromAnnotation(object, "handledFlags"))
                .build();
          }
        }
    );
    
    methodBodySuppliers.put(
        SpecificBooleanHandler.class.getName(),
        new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
          @Override
          public CodeBlock supplyFor(final AnnotationMirror object) {
            return CodeBlock
                .builder()
                .addStatement(
                    "final boolean defaultConsistentlyReturned = \n" +
                        "$1N().getBoolean($2L, true) == true && \n" +
                        "$1N().getBoolean($2L, false) == false",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(object, "attributeId"))
                .add("\n")
                .addStatement(
                    "return defaultConsistentlyReturned ? \n" +
                        "false : \n" +
                        "$N().getBoolean($L, true) == $L",
                    CallerDef.GET_ATTRS,
                    getLiteralFromAnnotation(object, "attributeId"),
                    getLiteralFromAnnotation(object, "handledBoolean"))
                .build();
          }
        }
    );
  }
  
  public SpecificValueIsAvailableMethodGenerator(final CoreHelpers coreHelpers) {
    checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");
    
    annotationMirrorHelper = coreHelpers.getAnnotationMirrorHelper();
  }
  
  /**
   * Creates a method spec equivalent to the following:
   * <pre>{@code
   * public boolean specificValueIsAvailable(final TypedArray attrs) {
   * 	dynamic implementation here
   * }}</pre>
   * <p>
   * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
   * queries the supplied typed array to determine if a specific value is available. The method returns true if the
   * value is available, and false otherwise. What exactly it means for a value to be available and which value is
   * of interest is defined by each specific implementation.
   *
   * @param callHandlerAnno
   *     the annotation to use when generating the method body, not null
   *
   * @return the method spec, not null
   *
   * @throws IllegalArgumentException
   *     if {@code callHandlerAnno} is null
   * @throws IllegalArgumentException
   *     if {@code callHandlerAnno} is not a call handler annotation
   */
  public MethodSpec generateFor(final AnnotationMirror callHandlerAnno) {
    checkNotNull(callHandlerAnno, "Argument \'callHandlerAnno\' cannot be null.");
    
    final String annoClassName = callHandlerAnno.getAnnotationType().toString();
    
    if (!methodBodySuppliers.containsKey(annoClassName)) {
      throw new IllegalArgumentException("Argument \'callHandlerAnno\' is not a call handler annotation.");
    }
    
    return MethodSpec
        .methodBuilder("specificValueIsAvailable")
        .returns(Boolean.class)
        .addCode(methodBodySuppliers.get(annoClassName).supplyFor(callHandlerAnno))
        .build();
  }
  
  private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
    return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
  }
}