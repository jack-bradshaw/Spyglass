package com.matthewtamlin.spyglass.processor.definitions;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import static javax.lang.model.element.Modifier.ABSTRACT;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

/**
 * Code generation definition for the Companion class.
 */
public class CompanionDef {
	public static final MethodSpec ACTIVATE_CALLERS;

	public static final TypeSpec INTERFACE;

	public static final JavaFile SRC_FILE;

	static {
		ACTIVATE_CALLERS = MethodSpec
				.methodBuilder("passDataToMethods")
				.addModifiers(PUBLIC, ABSTRACT)
				.returns(void.class)
				.build();

		INTERFACE = TypeSpec
				.interfaceBuilder("Companion")
				.addModifiers(PUBLIC)
				.addMethod(ACTIVATE_CALLERS)
				.build();

		SRC_FILE = JavaFile
				.builder("com.matthewtamlin.spyglass.core", INTERFACE)
				.addFileComment("Spyglass auto-generated file. Do not modify!")
				.skipJavaLangImports(true)
				.indent("\t")
				.build();
	}

	private CompanionDef() {
		throw new RuntimeException("Contract class. Do not instantiate.");
	}

	public static ClassName getCompanionAsClassName() {
		return ClassName.get(SRC_FILE.packageName, INTERFACE.name);
	}

	public static TypeSpec.Builder getNewCompanionImplementationPrototype(final String className) {
		return TypeSpec
				.classBuilder(className)
				.addSuperinterface(getCompanionAsClassName());
	}

	public static MethodSpec.Builder getNewActivateCallersMethodPrototype() {
		return MethodSpec
				.methodBuilder(ACTIVATE_CALLERS.name)
				.addModifiers(PUBLIC)
				.addAnnotation(Override.class)
				.returns(void.class);
	}
}
