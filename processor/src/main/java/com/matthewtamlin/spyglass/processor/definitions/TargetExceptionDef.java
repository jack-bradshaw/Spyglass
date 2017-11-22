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

package com.matthewtamlin.spyglass.processor.definitions;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class TargetExceptionDef {
	public static MethodSpec NO_ARG_CONSTRUCTOR;

	public static MethodSpec TARGET_ONLY_CONSTRUCTOR;

	public static MethodSpec CAUSE_ONLY_CONSTRUCTOR;

	public static MethodSpec TARGET_AND_CAUSE_CONSTRUCTOR;

	public static TypeSpec CLASS;

	public static JavaFile SRC_FILE;

	static {
		NO_ARG_CONSTRUCTOR = MethodSpec
				.constructorBuilder()
				.addModifiers(PUBLIC)
				.addCode(CodeBlock.of("super();\n"))
				.build();

		TARGET_ONLY_CONSTRUCTOR = MethodSpec
				.constructorBuilder()
				.addModifiers(PUBLIC)
				.addParameter(String.class, "message", FINAL)
				.addCode(CodeBlock.of("super(message);\n"))
				.build();

		CAUSE_ONLY_CONSTRUCTOR = MethodSpec
				.constructorBuilder()
				.addModifiers(PUBLIC)
				.addParameter(Throwable.class, "cause", FINAL)
				.addCode(CodeBlock.of("super(cause);\n"))
				.build();

		TARGET_AND_CAUSE_CONSTRUCTOR = MethodSpec
				.constructorBuilder()
				.addModifiers(PUBLIC)
				.addParameter(String.class, "message", FINAL)
				.addParameter(Throwable.class, "cause", FINAL)
				.addCode(CodeBlock.of("super(message, cause);\n"))
				.build();

		CLASS = TypeSpec
				.classBuilder("TargetException")
				.superclass(RuntimeException.class)
				.addModifiers(PUBLIC)
				.addMethod(NO_ARG_CONSTRUCTOR)
				.addMethod(TARGET_ONLY_CONSTRUCTOR)
				.addMethod(CAUSE_ONLY_CONSTRUCTOR)
				.addMethod(TARGET_AND_CAUSE_CONSTRUCTOR)
				.build();

		SRC_FILE = JavaFile
				.builder("com.matthewtamlin.spyglass.core", CLASS)
				.addFileComment("Spyglass auto-generated file. Do not modify!")
				.skipJavaLangImports(true)
				.indent("\t")
				.build();
	}

	public static TypeName getTargetExceptionAsClassname() {
		return ClassName.get(SRC_FILE.packageName, CLASS.name);
	}
}