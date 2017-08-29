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

	public boolean isEnum(final TypeMirror typeMirror) {
		final TypeMirror enumType = elementUtil.getTypeElement(Enum.class.getCanonicalName()).asType();

		return typeUtil.isAssignable(typeMirror, enumType);
	}

	public TypeMirror boxPrimitive(final TypeMirror typeMirror) {
		switch (typeMirror.toString()) {
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
				throw new IllegalArgumentException("Argument \'typeMirror\' must be primitive.");
		}
	}
}