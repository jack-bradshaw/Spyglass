package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.mirror_helpers.TypeMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class CastWrapperGenerator {
	private final Elements elementHelper;

	private final Types typeHelper;

	private final TypeMirrorHelper typeMirrorHelper;

	public CastWrapperGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		elementHelper = coreHelpers.getElementHelper();
		typeHelper = coreHelpers.getTypeHelper();
		typeMirrorHelper = coreHelpers.getTypeMirrorHelper();
	}

	public CodeBlock generateFor(final MethodSpec method, final TypeMirror recipient) {
		final TypeMirror methodReturnType = elementHelper.getTypeElement(method.returnType.toString()).asType();

		if (complexCastIsPossible(methodReturnType, recipient)) {
			return generateComplexCastWrapperFor(method, recipient);

		} else {
			return CodeBlock
					.builder()
					.add("($T) $N()", recipient, method)
					.build();
		}
	}

	private boolean complexCastIsPossible(final TypeMirror methodReturnType, final TypeMirror recipient) {
		return (typeMirrorHelper.isNumber(recipient) || typeMirrorHelper.isCharacter(recipient)) &&
				(typeMirrorHelper.isNumber(methodReturnType) || typeMirrorHelper.isCharacter(methodReturnType));
	}

	private CodeBlock generateComplexCastWrapperFor(final MethodSpec method, final TypeMirror recipient) {
		final TypeMirror methodReturnType = elementHelper.getTypeElement(method.returnType.toString()).asType();

		// If the method returns a character, an additional cast to byte is needed
		final CodeBlock toNumber = typeMirrorHelper.isCharacter(methodReturnType) ?
				CodeBlock.of("($T) ($T) $N()", Number.class, byte.class, method) :
				CodeBlock.of("($T) $N()", Number.class, method);

		if (recipient.toString().equals("byte")) {
			return CodeBlock.of("(byte) ($L).byteValue()", toNumber);

		} else if (elementHelper.getTypeElement(Byte.class.getCanonicalName()).equals(recipient)) {
			return CodeBlock.of("($T) ($L).byteValue()", Byte.class, toNumber);

		} else if (recipient.toString().equals("char")) {
			return CodeBlock.of("(char) ($L).byteValue()", toNumber);

		} else if (elementHelper.getTypeElement(Character.class.getCanonicalName()).equals(recipient)) {
			return CodeBlock.of("($T) ($L).byteValue()", Character.class, toNumber);

		} else if (recipient.toString().equals("short")) {
			return CodeBlock.of("(short) ($L).shortValue()", toNumber);

		} else if (elementHelper.getTypeElement(Short.class.getCanonicalName()).equals(recipient)) {
			return CodeBlock.of("($T) ($L).shortValue()", Short.class, toNumber);

		} else if (recipient.toString().equals("int")) {
			return CodeBlock.of("(int) ($L).intValue()", toNumber);

		} else if (elementHelper.getTypeElement(Integer.class.getCanonicalName()).equals(recipient)) {
			return CodeBlock.of("($T) ($L).intValue()", Integer.class, toNumber);

		} else if (recipient.toString().equals("long")) {
			return CodeBlock.of("(long) ($L).longValue()", toNumber);

		} else if (elementHelper.getTypeElement(Long.class.getCanonicalName()).equals(recipient)) {
			return CodeBlock.of("($T) ($L).longValue()", Long.class, toNumber);

		} else if (recipient.toString().equals("float")) {
			return CodeBlock.of("(float) ($L).floatValue()", toNumber);

		} else if (elementHelper.getTypeElement(Float.class.getCanonicalName()).equals(recipient)) {
			return CodeBlock.of("($T) ($L).floatValue()", Float.class, toNumber);

		} else if (recipient.toString().equals("double")) {
			return CodeBlock.of("(double) ($L).doubleValue()", toNumber);

		} else if (elementHelper.getTypeElement(Double.class.getCanonicalName()).equals(recipient)) {
			return CodeBlock.of("($T) ($L).doubleValue()", Double.class, toNumber);

		} else {
			return null;
		}
	}
}