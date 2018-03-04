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

package com.matthewtamlin.spyglass.processor.grouper;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkEachElementIsNotNull;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class Grouper {
  public static <T extends Element> Map<TypeElementWrapper, Set<T>> groupByEnclosingClass(final Set<T> elements) {
    checkNotNull(elements, "Argument \'elements\' cannot be null.");
    checkEachElementIsNotNull(elements, "Argument \'elements\' cannot contain null.");
    
    final Map<TypeElementWrapper, Set<T>> groups = new HashMap<>();
    
    for (final T element : elements) {
      final Element parent = element.getEnclosingElement();
      
      if (parent instanceof TypeElement) {
        final TypeElementWrapper parentWrapper = new TypeElementWrapper((TypeElement) parent);
        
        if (!groups.containsKey(parentWrapper)) {
          groups.put(parentWrapper, new HashSet<T>());
        }
        
        groups.get(parentWrapper).add(element);
      } else {
        throw new IllegalArgumentException("Argument \'elements\' contains an element which is not the " +
            "immediate child of a TypeElement.");
      }
    }
    
    return groups;
  }
  
  private Grouper() {
    throw new RuntimeException("Util class. Do not instantiate.");
  }
}