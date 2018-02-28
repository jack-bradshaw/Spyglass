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

import com.matthewtamlin.spyglass.processor.mirrorhelpers.TypeMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Inject;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class CastWrapperGenerator {
  private final Elements elementUtil;
  
  private final Types typeUtil;
  
  private final TypeMirrorHelper typeMirrorHelper;
  
  @Inject
  public CastWrapperGenerator(
      final Elements elementUtil,
      final Types typeUtil,
      final TypeMirrorHelper typeMirrorHelper) {
    
    this.elementUtil = checkNotNull(elementUtil);
    this.typeUtil = checkNotNull(typeUtil);
    this.typeMirrorHelper = checkNotNull(typeMirrorHelper);
  }
  
  public CodeBlock generateFor(final MethodSpec method, final TypeMirror recipient) {
    final TypeMirror methodReturnType = elementUtil.getTypeElement(method.returnType.toString()).asType();
    
    if (typeMirrorHelper.isNumber(recipient) && typeMirrorHelper.isNumber(methodReturnType)) {
      return generateNumberCastWrapperFor(method, recipient);
      
    } else if (typeMirrorHelper.isCharacter(recipient) && typeMirrorHelper.isCharacter(methodReturnType)) {
      return generateCharacterCastWrapperFor(method, recipient);
      
    } else {
      return CodeBlock
          .builder()
          .add("($T) $N()", recipient, method)
          .build();
    }
  }
  
  private CodeBlock generateNumberCastWrapperFor(final MethodSpec method, final TypeMirror recipient) {
    final TypeMirror methodReturnType = elementUtil.getTypeElement(method.returnType.toString()).asType();
    
    final CodeBlock toNumber = CodeBlock.of("($T) $N()", Number.class, method);
    
    if (recipient.toString().equals("byte")) {
      return CodeBlock.of("(byte) ($L).byteValue()", toNumber);
      
    } else if (recipient.toString().equals(Byte.class.getCanonicalName())) {
      return CodeBlock.of("($T) ($L).byteValue()", Byte.class, toNumber);
      
    } else if (recipient.toString().equals("short")) {
      return CodeBlock.of("(short) ($L).shortValue()", toNumber);
      
    } else if (recipient.toString().equals(Short.class.getCanonicalName())) {
      return CodeBlock.of("($T) ($L).shortValue()", Short.class, toNumber);
      
    } else if (recipient.toString().equals("int")) {
      return CodeBlock.of("(int) ($L).intValue()", toNumber);
      
    } else if (recipient.toString().equals(Integer.class.getCanonicalName())) {
      return CodeBlock.of("($T) ($L).intValue()", Integer.class, toNumber);
      
    } else if (recipient.toString().equals("long")) {
      return CodeBlock.of("(long) ($L).longValue()", toNumber);
      
    } else if (recipient.toString().equals(Long.class.getCanonicalName())) {
      return CodeBlock.of("($T) ($L).longValue()", Long.class, toNumber);
      
    } else if (recipient.toString().equals("float")) {
      return CodeBlock.of("(float) ($L).floatValue()", toNumber);
      
    } else if (recipient.toString().equals(Float.class.getCanonicalName())) {
      return CodeBlock.of("($T) ($L).floatValue()", Float.class, toNumber);
      
    } else if (recipient.toString().equals("double")) {
      return CodeBlock.of("(double) ($L).doubleValue()", toNumber);
      
    } else if (recipient.toString().equals(Double.class.getCanonicalName())) {
      return CodeBlock.of("($T) ($L).doubleValue()", Double.class, toNumber);
      
    } else {
      return CodeBlock.of("($T) $N()", methodReturnType, method);
    }
  }
  
  private CodeBlock generateCharacterCastWrapperFor(final MethodSpec method, final TypeMirror recipient) {
    final TypeMirror methodReturnType = elementUtil.getTypeElement(method.returnType.toString()).asType();
    
    if (recipient.toString().equals("char")) {
      return CodeBlock.of("(char) $N()", method);
      
    } else if (recipient.toString().equals(Character.class.getCanonicalName())) {
      return CodeBlock.of("($T) $N()", Character.class, method);
      
    } else {
      return CodeBlock.of("($T) $N()", methodReturnType, method);
    }
  }
}