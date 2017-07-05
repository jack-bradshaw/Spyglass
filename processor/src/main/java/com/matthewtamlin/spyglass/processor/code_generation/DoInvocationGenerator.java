package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.exception.SpyglassRuntimeException;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.CallHandlerAnnoUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.UseAnnoUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.ValueHandlerAnnoUtil;
import com.matthewtamlin.spyglass.processor.mirror_utils.TypeMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.Modifier.FINAL;

@Tested(testMethod = "automated")
public class DoInvocationGenerator {
	private static final String CAN_ASSIGN = "$T.class.isAssignableFrom(value.getClass())";

	private final Elements elementUtil;

	private final Types typeUtil;

	private TypeMirrorHelper typeMirrorHelper;

	public DoInvocationGenerator(final Elements elementUtil, final Types typeUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
		this.typeUtil = checkNotNull(typeUtil, "Argument \'typeUtil\' cannot be null.");

		this.typeMirrorHelper = new TypeMirrorHelper(elementUtil, typeUtil);
	}

	public MethodSpec getMethod(final ExecutableElement method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		if (CallHandlerAnnoUtil.hasAnnotation(method)) {
			return getMethodForCallHandlerCase(method);
		} else if (ValueHandlerAnnoUtil.hasValueHandlerAnnotation(method)) {
			return getMethodForValueHandlerCase(method);
		} else {
			throw new IllegalArgumentException("Argument \'element\' must have a handler annotation.");
		}
	}

	private MethodSpec getMethodForCallHandlerCase(final ExecutableElement method) {
		return MethodSpec
				.methodBuilder("doInvocation")
				.returns(TypeName.VOID)
				.addParameter(TypeName.get(method.getEnclosingElement().asType()), "target", FINAL)
				.addCode(getInvocationLineWithoutRecipient(method))
				.build();
	}

	private MethodSpec getMethodForValueHandlerCase(final ExecutableElement method) {
		final CodeBlock.Builder block = CodeBlock.builder();

		addNullCheckComponent(block, method);
		addConversionCheckComponent(block, method);
		addElseComponent(block, method);

		return MethodSpec
				.methodBuilder("doInvocation")
				.returns(TypeName.VOID)
				.addParameter(TypeName.get(method.getEnclosingElement().asType()), "target", FINAL)
				.addParameter(TypeName.OBJECT, "value", FINAL)
				.addCode(block.build())
				.build();
	}

	private TypeMirror getRecipientType(final ExecutableElement method) {
		for (final VariableElement parameter : method.getParameters()) {
			if (!UseAnnoUtil.hasUseAnnotation(parameter)) {
				return parameter.asType();
			}
		}

		return null;
	}

	private CodeBlock getInvocationLineWithoutRecipient(final ExecutableElement method) {
		final List<CodeBlock> args = getArgumentsForMethod(method);
		return convertArgumentsToMethodCall(method, args);
	}

	private CodeBlock getInvocationLineWithRecipient(final ExecutableElement method, final CodeBlock recipientCode) {
		final List<CodeBlock> args = getArgumentsForMethod(method);
		args.set(args.indexOf(null), recipientCode);

		return convertArgumentsToMethodCall(method, args);
	}

	private List<CodeBlock> getArgumentsForMethod(final ExecutableElement method) {
		final List<CodeBlock> codeBlocks = new ArrayList<>();

		for (final VariableElement parameter : method.getParameters()) {
			if (UseAnnoUtil.hasUseAnnotation(parameter)) {
				final AnnotationMirror useAnnotationMirror = UseAnnoUtil.getUseAnnotationMirror(parameter);
				codeBlocks.add(getArgumentForUseAnnotation(useAnnotationMirror));
			} else {
				codeBlocks.add(null);
			}
		}

		return codeBlocks;
	}

	private CodeBlock getArgumentForUseAnnotation(final AnnotationMirror useAnnotationMirror) {
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

	private CodeBlock convertArgumentsToMethodCall(final ExecutableElement method, final List<CodeBlock> args) {
		final CodeBlock.Builder invocationLine = CodeBlock
				.builder()
				.add("target.$L(", method.getSimpleName());

		for (int i = 0; i < args.size(); i++) {
			invocationLine.add(args.get(i));

			if (i < method.getParameters().size() - 1) {
				invocationLine.add(", ");
			}
		}

		invocationLine.add(");\n");

		return invocationLine.build();
	}

	private void addNullCheckComponent(final CodeBlock.Builder codeBlock, final ExecutableElement method) {
		final TypeMirror recipientType = getRecipientType(method);

		codeBlock.beginControlFlow("if (value == null)");

		if (typeMirrorHelper.isPrimitive(recipientType)) {
			codeBlock.add(getCannotPassNullExceptionCodeFor(method));
		} else {
			codeBlock.add(getInvocationLineWithRecipient(method, CodeBlock.of("($T) null", recipientType)));
		}
	}

	private void addConversionCheckComponent(final CodeBlock.Builder block, final ExecutableElement method) {
		final TypeMirror recipientType = getRecipientType(method);

		if (typeMirrorHelper.isNumber(recipientType) || typeMirrorHelper.isCharacter(recipientType)) {
			block.nextControlFlow("else if (" + CAN_ASSIGN + " || " + CAN_ASSIGN + ")", Number.class, Character.class);
			block.add(getInvocationLineWithRecipient(method, getNumberConversionCode(recipientType)));

		} else if (typeMirrorHelper.isPrimitive(recipientType)) {
			block.nextControlFlow("else if (" + CAN_ASSIGN + ")", typeMirrorHelper.boxPrimitive(recipientType));
			block.add(getInvocationLineWithRecipient(method, CodeBlock.of("($T) value", recipientType)));

		} else {
			block.nextControlFlow("else if (" + CAN_ASSIGN + ")", recipientType);
			block.add(getInvocationLineWithRecipient(method, CodeBlock.of("($T) value", recipientType)));
		}
	}

	private void addElseComponent(final CodeBlock.Builder codeBlock, final ExecutableElement method) {
		codeBlock
				.nextControlFlow("else")
				.add(getCannotPassTypeExceptionCodeFor(method))
				.endControlFlow();
	}

	private CodeBlock getCannotPassNullExceptionCodeFor(final ExecutableElement method) {
		return CodeBlock.of("throw new $T(\"Spyglass cannot pass null to method $L in class $L.\");",
				SpyglassRuntimeException.class,
				method.getSimpleName(),
				method.getEnclosingElement().getSimpleName());
	}

	private CodeBlock getCannotPassTypeExceptionCodeFor(final ExecutableElement method) {
		return CodeBlock.of(
				"throw new $T(\"Spyglass cannot pass the specified data type to method $L in class $L.\");",
				SpyglassRuntimeException.class,
				method.getSimpleName(),
				method.getEnclosingElement().getSimpleName());
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
}