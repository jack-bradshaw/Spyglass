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

import com.matthewtamlin.java_utilities.testing.Tested;

import javax.lang.model.element.TypeElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class TypeElementWrapper {
  private final TypeElement element;
  
  public TypeElementWrapper(final TypeElement element) {
    this.element = checkNotNull(element, "Argument \'element\' cannot be null.");
  }
  
  public TypeElement unwrap() {
    return element;
  }
  
  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    } else if (o == null) {
      return false;
    } else if (this.getClass() != o.getClass()) {
      return false;
    } else {
      final TypeElementWrapper castInput = (TypeElementWrapper) o;
      
      final String inputQualifiedName = castInput.element.getQualifiedName().toString();
      final String thisQualifiedName = this.element.getQualifiedName().toString();
      
      return inputQualifiedName.equals(thisQualifiedName);
    }
  }
  
  @Override
  public int hashCode() {
    return element.getQualifiedName().toString().hashCode();
  }
}