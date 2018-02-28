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

package com.matthewtamlin.spyglass.processor.annotationretrievers;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.definitions.AnnotationRegistry;
import com.matthewtamlin.spyglass.processor.mirrorhelpers.AnnotationMirrorHelper;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class UnconditionalHandlerRetriever {
  public static AnnotationMirror getAnnotation(final ExecutableElement element) {
    checkNotNull(element, "Argument \'element\' cannot be null.");

    for (final Class<? extends Annotation> annotationClass : AnnotationRegistry.UNCONDITIONAL_HANDLERS) {
      final AnnotationMirror mirror = AnnotationMirrorHelper.getAnnotationMirror(element, annotationClass);

      if (mirror != null) {
        return mirror;
      }
    }

    return null;
  }

  public static boolean hasAnnotation(final ExecutableElement element) {
    return getAnnotation(element) != null;
  }

  private UnconditionalHandlerRetriever() {
    throw new RuntimeException("Utility class. Do not instantiate.");
  }
}