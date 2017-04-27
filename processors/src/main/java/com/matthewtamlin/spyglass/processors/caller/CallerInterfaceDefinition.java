package com.matthewtamlin.spyglass.processors.caller;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.PUBLIC;

public final class CallerInterfaceDefinition {
	private static final ClassName androidContext = ClassName.get(
			"android.content",
			"Context");

	private static final ClassName androidTypedArray = ClassName.get(
			"android.content.res",
			"TypedArray");

	public static final String PACKAGE = "com.matthewtamlin.spyglass.processors.caller";

	public static final String INTERFACE_NAME = "Caller";

	public static final String METHOD_NAME = "callIfSatisfied";

	public static JavaFile getJavaFile() {
		final TypeVariableName targetType = TypeVariableName.get("T");

		final MethodSpec methodSpec = MethodSpec
				.methodBuilder(METHOD_NAME)
				.addModifiers(PUBLIC, ABSTRACT)
				.returns(void.class)
				.addParameter(targetType, "target")
				.addParameter(androidContext, "context")
				.addParameter(androidTypedArray, "attributes")
				.build();

		final TypeSpec interfaceSpec = TypeSpec
				.interfaceBuilder(INTERFACE_NAME)
				.addModifiers(PUBLIC, ABSTRACT)
				.addTypeVariable(targetType)
				.addMethod(methodSpec)
				.build();

		return JavaFile
				.builder(PACKAGE, interfaceSpec)
				.addFileComment("Spyglass auto-generated file. Do not modify!")
				.build();
	}

	private CallerInterfaceDefinition() {
		throw new RuntimeException("Contract class. Do not instantiate.");
	}
}