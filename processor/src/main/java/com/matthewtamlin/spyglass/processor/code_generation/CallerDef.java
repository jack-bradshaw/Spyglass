package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.PROTECTED;
import static javax.lang.model.element.Modifier.PUBLIC;

@Tested(testMethod = "automated")
public final class CallerDef {
	public static final MethodSpec GET_CONTEXT;

	public static final MethodSpec GET_TARGET;

	public static final MethodSpec GET_ATTRS;

	public static final MethodSpec CALL;

	public static final MethodSpec CONSTRUCTOR;

	public static final TypeSpec ABSTRACT_CALLER;

	public static final JavaFile SRC_FILE;

	static {
		final TypeVariableName targetType = TypeVariableName.get("T");

		GET_CONTEXT = MethodSpec
				.methodBuilder("getContext")
				.addModifiers(PROTECTED)
				.returns(AndroidClassNames.CONTEXT)
				.addCode(CodeBlock.of("return context;"))
				.build();

		GET_TARGET = MethodSpec
				.methodBuilder("getTarget")
				.addModifiers(PROTECTED)
				.returns(targetType)
				.addCode(CodeBlock.of("return target;"))
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
				.returns(void.class)
				.build();

		CONSTRUCTOR = MethodSpec
				.constructorBuilder()
				.addModifiers(PUBLIC)
				.addParameter(AndroidClassNames.CONTEXT, "context", FINAL)
				.addParameter(targetType, "target", FINAL)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
				.addCode(CodeBlock
						.builder()
						.addStatement("this.context = context")
						.addStatement("this.target = target")
						.addStatement("this.attrs = attrs")
						.build())
				.build();

		ABSTRACT_CALLER = TypeSpec
				.classBuilder("Caller")
				.addModifiers(PUBLIC, ABSTRACT)
				.addTypeVariable(targetType)
				.addMethod(GET_CONTEXT)
				.addMethod(GET_TARGET)
				.addMethod(GET_ATTRS)
				.addMethod(CALL)
				.addMethod(CONSTRUCTOR)
				.addField(AndroidClassNames.CONTEXT, "context", PRIVATE, FINAL)
				.addField(targetType, "target", PRIVATE, FINAL)
				.addField(AndroidClassNames.TYPED_ARRAY, "attrs", PRIVATE, FINAL)
				.build();

		SRC_FILE = JavaFile
				.builder("com.matthewtamlin.spyglass.processors.code_generation", ABSTRACT_CALLER)
				.addFileComment("Spyglass auto-generated file. Do not modify!")
				.skipJavaLangImports(true)
				.indent("\t")
				.build();
	}

	public static ClassName asClassName() {
		return ClassName.get(SRC_FILE.packageName, ABSTRACT_CALLER.name);
	}

	private CallerDef() {
		throw new RuntimeException("Contract class. Do not instantiate.");
	}
}