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

package com.matthewtamlin.spyglass.processor.mirrorhelpers;

import com.matthewtamlin.java_utilities.testing.Tested;

import javax.inject.Inject;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class AnnotationMirrorHelper {
  private Elements elementHelper;
  
  @Inject
  public AnnotationMirrorHelper(final Elements elementHelper) {
    this.elementHelper = checkNotNull(elementHelper, "Argument \'elementHelper\' cannot be null.");
  }
  
  public static AnnotationMirror getAnnotationMirror(
      final Element element,
      final Class<? extends Annotation> annotationClass) {
    
    checkNotNull(element, "Argument \'element\' cannot be null.");
    checkNotNull(annotationClass, "Argument \'annotationClass\' cannot be null.");
    
    for (final AnnotationMirror mirror : element.getAnnotationMirrors()) {
      if (mirror.getAnnotationType().toString().equals(annotationClass.getName())) {
        return mirror;
      }
    }
    
    return null;
  }
  
  public AnnotationValue getValueIgnoringDefaults(final AnnotationMirror mirror, final String valueKey) {
    checkNotNull(mirror, "Argument \'mirror\' cannot be null.");
    checkNotNull(valueKey, "Argument \'valueKey\' cannot be null.");
    
    final Map<? extends ExecutableElement, ? extends AnnotationValue> values = mirror.getElementValues();
    
    for (final ExecutableElement mapKey : values.keySet()) {
      if (mapKey.getSimpleName().toString().equals(valueKey)) {
        return values.get(mapKey);
      }
    }
    
    return null;
  }
  
  public AnnotationValue getValueUsingDefaults(final AnnotationMirror mirror, final String valueKey) {
    checkNotNull(mirror, "Argument \'mirror\' cannot be null.");
    checkNotNull(valueKey, "Argument \'valueKey\' cannot be null.");
    
    final Map<? extends ExecutableElement, ? extends AnnotationValue> values = elementHelper
        .getElementValuesWithDefaults(mirror);
    
    for (final ExecutableElement mapKey : values.keySet()) {
      if (mapKey.getSimpleName().toString().equals(valueKey)) {
        return values.get(mapKey);
      }
    }
    
    return null;
  }
}