package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.exception.SpyglassRuntimeException;
import com.matthewtamlin.spyglass.processor.annotation_utils.AnnotationMirrorUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.CallHandlerAnnotationUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.UseAnnotationUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.ValueHandlerAnnotationUtil;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.Modifier.FINAL;

public class DoInvocationGenerator {
	private static final String ASSIGNABLE_FROM_VALUE_SNIPPET = "$T.class.isAssignableFrom(value.getClass())";

	private final Elements elementUtil;

	private final Types typeUtil;

	public DoInvocationGenerator(final Elements elementUtil, final Types typeUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
		this.typeUtil = checkNotNull(typeUtil, "Argument \'typeUtil\' cannot be null.");
	}

	public MethodSpec getMethod(final ExecutableElement method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		if (CallHandlerAnnotationUtil.hasCallHandlerAnnotation(method)) {
			return getMethodForCallHandlerCase(method);
		} else if (ValueHandlerAnnotationUtil.hasValueHandlerAnnotation(method)) {
			return getMethodForValueHandlerCase(method);
		} else {
			throw new IllegalArgumentException("Argument \'element\' must have a handler annotation.");
		}
	}

	private MethodSpec getMethodForCallHandlerCase(final ExecutableElement method) {
		final TypeName targetTypeName = TypeName.get(method.getEnclosingElement().asType());

		return MethodSpec
				.methodBuilder("doInvocation")
				.returns(TypeName.VOID)
				.addParameter(targetTypeName, "target", FINAL)
				.addCode(getInvocationLine(method, null))
				.build();
	}

	private MethodSpec getMethodForValueHandlerCase(final ExecutableElement method) {
		final MethodSpec.Builder methodSpecBuilder = MethodSpec
				.methodBuilder("doInvocation")
				.returns(TypeName.VOID)
				.addParameter(TypeName.get(method.getEnclosingElement().asType()), "target", FINAL)
				.addParameter(TypeName.OBJECT, "value", FINAL);

		final TypeMirror recipientType = getRecipientType(method);

		final CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.beginControlFlow("if (value == null)");

		if (isPrimitive(recipientType)) {
			codeBlockBuilder.addStatement(
					"throw new $T(\"Spyglass cannot pass null to method $L in class $L.\")",
					SpyglassRuntimeException.class,
					method.getSimpleName(),
					method.getEnclosingElement().getSimpleName());
		} else {
			final CodeBlock nonUseArg = CodeBlock.of("($T) null", recipientType);
			codeBlockBuilder.add(getInvocationLine(method, nonUseArg));
		}

		if (isNumber(recipientType) || isCharacter(recipientType)) {
			codeBlockBuilder.nextControlFlow(
					"else if (" + ASSIGNABLE_FROM_VALUE_SNIPPET + " || " + ASSIGNABLE_FROM_VALUE_SNIPPET + ")",
					Number.class,
					Character.class);

			codeBlockBuilder.add(getInvocationLine(method, getNumberConversionCode(recipientType)));

		} else if (isPrimitive(recipientType)) {
			codeBlockBuilder.nextControlFlow("else if (" + ASSIGNABLE_FROM_VALUE_SNIPPET + ")", box(recipientType));
			codeBlockBuilder.add(getInvocationLine(method, CodeBlock.of("($T) value", recipientType)));
		} else {
			codeBlockBuilder.nextControlFlow("else if (" + ASSIGNABLE_FROM_VALUE_SNIPPET + ")", recipientType);
			codeBlockBuilder.add(getInvocationLine(method, CodeBlock.of("($T) value", recipientType)));
		}

		codeBlockBuilder
				.nextControlFlow("else")
				.addStatement(
						"throw new $T(\"Spyglass cannot pass data of type $L to method $L in class $L.\")",
						SpyglassRuntimeException.class,
						recipientType.toString(),
						method.getSimpleName(),
						method.getEnclosingElement().getSimpleName())
				.endControlFlow();

		methodSpecBuilder.addCode(codeBlockBuilder.build());

		return methodSpecBuilder.build();
	}

	private CodeBlock getInvocationLine(final ExecutableElement method, final CodeBlock nonUseArgValue) {
		final CodeBlock.Builder invocationLine = CodeBlock
				.builder()
				.add("target.$L(", method.getSimpleName());

		for (int i = 0; i < method.getParameters().size(); i++) {
			final VariableElement parameter = method.getParameters().get(i);

			if (UseAnnotationUtil.hasUseAnnotation(parameter)) {
				invocationLine.add(getUseAnnotationCode(parameter));

			} else if (nonUseArgValue == null) {
				throw new RuntimeException("A non-use arg is required for value handler cases.");

			} else {
				invocationLine.add(nonUseArgValue);
			}

			if (i < method.getParameters().size() - 1) {
				invocationLine.add(", ");
			}
		}

		invocationLine.add(");\n");

		return invocationLine.build();
	}

	private TypeMirror getRecipientType(final ExecutableElement method) {
		for (final VariableElement parameter : method.getParameters()) {
			if (!UseAnnotationUtil.hasUseAnnotation(parameter)) {
				return parameter.asType();
			}
		}

		return null;
	}

	private boolean isPrimitive(final TypeMirror typeMirror) {
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

	private boolean isNumber(final TypeMirror typeMirror) {
		final String typeMirrorString = typeMirror.toString();

		final TypeMirror numberType = elementUtil.getTypeElement(Number.class.getCanonicalName()).asType();

		return typeUtil.isAssignable(typeMirror, numberType) ||
				typeMirrorString.equals("byte") ||
				typeMirrorString.equals("char") ||
				typeMirrorString.equals("short") ||
				typeMirrorString.equals("int") ||
				typeMirrorString.equals("long") ||
				typeMirrorString.equals("double") ||
				typeMirrorString.equals("float");
	}

	private boolean isCharacter(final TypeMirror typeMirror) {
		final TypeMirror characterType = elementUtil.getTypeElement(Character.class.getCanonicalName()).asType();
		return typeUtil.isAssignable(typeMirror, characterType);
	}

	private CodeBlock getNumberConversionCode(final TypeMirror targetTypeMirror) {
		final String targetType = targetTypeMirror.toString();

		if (targetType.equals("byte") || targetType.equals(Byte.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).byteValue()", targetTypeMirror);

		} else if (targetType.equals("char") || targetType.equals(Character.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).byteValue()", targetTypeMirror);

		} else if (targetType.equals("short") || targetType.equals(Short.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).shortValue()", targetTypeMirror);

		} else if (targetType.equals("int") || targetType.equals(Integer.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).intValue()", targetTypeMirror);

		} else if (targetType.equals("long") || targetType.equals(Long.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).longValue()", targetTypeMirror);

		} else if (targetType.equals("float") || targetType.equals(Float.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).floatValue()", targetTypeMirror);

		} else if (targetType.equals("double") || targetType.equals(Double.class.getCanonicalName())) {
			return CodeBlock.of("($T) ((Number) value).doubleValue()", targetTypeMirror);

		} else {
			throw new IllegalArgumentException("Argument \'recipientTypeMirror\' is not a number.");
		}
	}

	private TypeMirror box(final TypeMirror typeMirror) {
		final String typeMirrorString = typeMirror.toString();

		switch (typeMirrorString) {
			case "byte":
				return elementUtil.getTypeElement(Byte.class.getCanonicalName()).asType();
			case "char":
				return elementUtil.getTypeElement(Character.class.getCanonicalName()).asType();
			case "short":
				return elementUtil.getTypeElement(Short.class.getCanonicalName()).asType();
			case "int":
				return elementUtil.getTypeElement(Integer.class.getCanonicalName()).asType();
			case "long":
				return elementUtil.getTypeElement(Long.class.getCanonicalName()).asType();
			case "float":
				return elementUtil.getTypeElement(Float.class.getCanonicalName()).asType();
			case "double":
				return elementUtil.getTypeElement(Double.class.getCanonicalName()).asType();
			case "boolean":
				return elementUtil.getTypeElement(Boolean.class.getCanonicalName()).asType();
			default:
				throw new IllegalArgumentException("Argument \'recipientTypeMirror\' is not a number.");
		}
	}

	private CodeBlock getUseAnnotationCode(final VariableElement parameter) {
		final AnnotationMirror useAnnotationMirror = UseAnnotationUtil.getUseAnnotationMirror(parameter);
		final String useAnnotationName = useAnnotationMirror.getAnnotationType().toString();

		if (useAnnotationName.equals(UseShort.class.getName())) {
			final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
					useAnnotationMirror,
					"value",
					elementUtil);

			return CodeBlock.of("(short)" + rawValue.toString());

		} else if (useAnnotationName.equals(UseNull.class.getName())) {
			return CodeBlock.of("null");

		} else {
			final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
					useAnnotationMirror,
					"value",
					elementUtil);

			return CodeBlock.of(rawValue.toString());
		}
	}
}