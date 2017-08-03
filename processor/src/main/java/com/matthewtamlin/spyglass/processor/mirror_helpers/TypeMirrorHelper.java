package com.matthewtamlin.spyglass.processor.mirror_helpers;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.util.SetUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

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

		return SetUtil.allToString(getAllSupertypes(typeMirror)).contains(numberType.toString()) ||
				typeMirror.toString().equals(numberType.toString()) ||
				typeMirror.toString().equals("byte") ||
				typeMirror.toString().equals("short") ||
				typeMirror.toString().equals("int") ||
				typeMirror.toString().equals("long") ||
				typeMirror.toString().equals("double") ||
				typeMirror.toString().equals("float");
	}

	public boolean isCharacter(final TypeMirror typeMirror) {
		final TypeMirror characterType = elementUtil.getTypeElement(Character.class.getCanonicalName()).asType();

		return SetUtil.allToString(getAllSupertypes(typeMirror)).contains(characterType.toString()) ||
				typeMirror.toString().equals(characterType.toString()) ||
				typeMirror.toString().equals("char");
	}

	public boolean isBoolean(final TypeMirror typeMirror) {
		final TypeMirror booleanType = elementUtil.getTypeElement(Boolean.class.getCanonicalName()).asType();

		return typeMirror.toString().equals(booleanType.toString()) || typeMirror.toString().equals("boolean");
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

	private Set<TypeMirror> getAllSupertypes(final TypeMirror typeMirror) {
		final Set<TypeMirror> exploredSupertypes = new HashSet<>();
		final Stack<TypeMirror> newSupertypes = new Stack<>();

		newSupertypes.addAll(typeUtil.directSupertypes(typeMirror));

		while (!newSupertypes.isEmpty()) {
			final TypeMirror type = newSupertypes.pop();
			exploredSupertypes.add(type);

			newSupertypes.addAll(typeUtil.directSupertypes(type));
		}

		return exploredSupertypes;
	}

	public boolean isAssignable(final TypeMirror supplied, final TypeMirror recipient) {
		checkNotNull(supplied, "Argument \'supplied\' cannot be null.");
		checkNotNull(recipient, "Argument \'recipient\' cannot be null.");

		final Set<TypeMirror> superclasses = getAllSupertypes(supplied);
		final Set<String> superclassNames = SetUtil.allToString(superclasses);

		return superclassNames.contains(recipient.toString()) || supplied.toString().equals(recipient.toString());
	}
}