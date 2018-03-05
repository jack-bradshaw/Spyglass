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

package com.matthewtamlin.spyglass.processor.definitions;

import com.squareup.javapoet.*;

import static javax.lang.model.element.Modifier.*;

/**
 * Code generation definition for the Caller class.
 */
public final class CallerDef {
  public static final MethodSpec GET_TARGET;
  
  public static final MethodSpec GET_CONTEXT;
  
  public static final MethodSpec GET_ATTRS;
  
  public static final MethodSpec CALL;
  
  public static final MethodSpec CONSTRUCTOR;
  
  public static final TypeSpec ABSTRACT_CALLER;
  
  public static final JavaFile SRC_FILE;
  
  static {
    final TypeVariableName targetType = TypeVariableName.get("T");
    
    GET_TARGET = MethodSpec
        .methodBuilder("getTarget")
        .addModifiers(PROTECTED)
        .returns(targetType)
        .addCode(CodeBlock.of("return target;"))
        .build();
    
    GET_CONTEXT = MethodSpec
        .methodBuilder("getContext")
        .addModifiers(PROTECTED)
        .returns(AndroidClassNames.CONTEXT)
        .addCode(CodeBlock.of("return context;"))
        .build();
    
    GET_ATTRS = MethodSpec
        .methodBuilder("getAttrs")
        .addModifiers(PROTECTED)
        .returns(AndroidClassNames.TYPED_ARRAY)
        .addCode(CodeBlock.of("return attrs;"))
        .build();
    
    CALL = MethodSpec
        .methodBuilder("call")
        .addModifiers(PUBLIC, ABSTRACT)
        .returns(RxJavaClassNames.COMPLETABLE)
        .build();
    
    CONSTRUCTOR = MethodSpec
        .constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(targetType, "target", FINAL)
        .addParameter(AndroidClassNames.CONTEXT, "context", FINAL)
        .addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
        .addCode(CodeBlock
            .builder()
            .addStatement("this.target = target")
            .addStatement("this.context = context")
            .addStatement("this.attrs = attrs")
            .build())
        .build();
    
    ABSTRACT_CALLER = TypeSpec
        .classBuilder("Caller2")
        .addModifiers(PUBLIC, ABSTRACT)
        .addTypeVariable(targetType)
        .addMethod(GET_TARGET)
        .addMethod(GET_CONTEXT)
        .addMethod(GET_ATTRS)
        .addMethod(CALL)
        .addMethod(CONSTRUCTOR)
        .addField(targetType, "target", PRIVATE, FINAL)
        .addField(AndroidClassNames.CONTEXT, "context", PRIVATE, FINAL)
        .addField(AndroidClassNames.TYPED_ARRAY, "attrs", PRIVATE, FINAL)
        .build();
    
    SRC_FILE = JavaFile
        .builder("com.matthewtamlin.spyglass.core", ABSTRACT_CALLER)
        .addFileComment("Spyglass auto-generated file. Do not modify!")
        .skipJavaLangImports(true)
        .indent("\t")
        .build();
  }
  
  public static ClassName getCallerAsClassName() {
    return ClassName.get(SRC_FILE.packageName, ABSTRACT_CALLER.name);
  }
  
  public static MethodSpec.Builder getNewCallMethodPrototype() {
    return MethodSpec
        .methodBuilder(CALL.name)
        .returns(RxJavaClassNames.COMPLETABLE)
        .addModifiers(PUBLIC);
  }
  
  public static MethodSpec.Builder getNewConstructorPrototype(final TypeName targetType) {
    return MethodSpec
        .constructorBuilder()
        .addModifiers(PUBLIC)
        .addParameter(targetType, "target", FINAL)
        .addParameter(AndroidClassNames.CONTEXT, "context", FINAL)
        .addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
        .addCode(CodeBlock
            .builder()
            .addStatement("super(target, context, attrs)")
            .build());
  }
  
  public static TypeSpec.Builder getNewCallerSubclassPrototype(final String className, final TypeName targetType) {
    final ClassName genericCaller = CallerDef.getCallerAsClassName();
    final TypeName specificCaller = ParameterizedTypeName.get(genericCaller, targetType);
    
    return TypeSpec
        .classBuilder(className)
        .superclass(specificCaller);
  }
  
  public static TypeSpec.Builder getNewAnonymousCallerPrototype(
      final TypeName targetType,
      final CodeBlock targetParameter,
      final CodeBlock contextParameter,
      final CodeBlock attrsParameter) {
    
    final ClassName genericCaller = CallerDef.getCallerAsClassName();
    final TypeName specificCaller = ParameterizedTypeName.get(genericCaller, targetType);
    
    return TypeSpec
        .anonymousClassBuilder(
            CodeBlock.of("$L, $L, $L", targetParameter, contextParameter, attrsParameter).toString())
        .addSuperinterface(specificCaller);
  }
  
  private CallerDef() {
    throw new RuntimeException("Contract class. Do not instantiate.");
  }
}