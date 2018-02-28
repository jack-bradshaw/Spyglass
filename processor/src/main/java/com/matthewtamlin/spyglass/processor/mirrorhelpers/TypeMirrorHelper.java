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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class TypeMirrorHelper {
  private Elements elementUtil;
  
  private Types typeUtil;
  
  @Inject
  public TypeMirrorHelper(final Elements elementHelper, final Types typeHelper) {
    this.elementUtil = checkNotNull(elementHelper, "Argument \'elementHelper\' cannot be null.");
    this.typeUtil = checkNotNull(typeHelper, "Argument \'typeUtil\' cannot be null.");
  }
  
  public boolean isPrimitive(final TypeMirror typeMirror) {
    final String typeMirrorString = typeMirror.toString();
    
    return typeMirrorString.equals("byte") ||
        typeMirrorString.equals("char") ||
        typeMirrorString.equals("short") ||
        typeMirrorString.equals("int") ||
        typeMirrorString.equals("long") ||
        typeMirrorString.equals("double") ||
        typeMirrorString.equals("float") ||
        typeMirrorString.equals("boolean");
  }
  
  public boolean isNumber(final TypeMirror typeMirror) {
    final TypeMirror numberType = elementUtil.getTypeElement(Number.class.getCanonicalName()).asType();
    
    return typeUtil.isAssignable(typeMirror, numberType) ||
        typeMirror.toString().equals("byte") ||
        typeMirror.toString().equals("short") ||
        typeMirror.toString().equals("int") ||
        typeMirror.toString().equals("long") ||
        typeMirror.toString().equals("double") ||
        typeMirror.toString().equals("float");
  }
  
  public boolean isCharacter(final TypeMirror typeMirror) {
    final TypeMirror characterType = elementUtil.getTypeElement(Character.class.getCanonicalName()).asType();
    
    return typeUtil.isAssignable(typeMirror, characterType) || typeMirror.toString().equals("char");
  }
  
  public boolean isBoolean(final TypeMirror typeMirror) {
    final TypeMirror booleanType = elementUtil.getTypeElement(Boolean.class.getCanonicalName()).asType();
    
    return typeUtil.isAssignable(typeMirror, booleanType) || typeMirror.toString().equals("boolean");
  }
}