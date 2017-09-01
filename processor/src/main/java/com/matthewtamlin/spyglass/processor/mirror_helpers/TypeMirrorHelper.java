package com.matthewtamlin.spyglass.processor.mirror_helpers;

import com.matthewtamlin.java_utilities.testing.Tested;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class TypeMirrorHelper {
	private Elements elementUtil;

	private Types typeUtil;

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