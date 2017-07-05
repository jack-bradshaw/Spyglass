package com.matthewtamlin.spyglass.processor.annotation_info;

import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.processor.util.SetUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class UseAnnoInfo {
	public static final Set<Class<? extends Annotation>> ALL_ANNOS = SetUtil.unmodifiableSetOf(
			UseBoolean.class,
			UseByte.class,
			UseChar.class,
			UseDouble.class,
			UseFloat.class,
			UseInt.class,
			UseLong.class,
			UseNull.class,
			UseShort.class,
			UseString.class);

	private final Map<String, TypeMirror> definedTypes = new HashMap<>();

	private final Elements elementUtil;

	public UseAnnoInfo(final Elements elementUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \' elementUtil\' cannot be null.");

		definedTypes.put(UseBoolean.class.getName(), getTypeMirrorFor(Boolean.class.getCanonicalName()));
		definedTypes.put(UseByte.class.getName(), getTypeMirrorFor(Byte.class.getCanonicalName()));
		definedTypes.put(UseChar.class.getName(), getTypeMirrorFor(Character.class.getCanonicalName()));
		definedTypes.put(UseDouble.class.getName(), getTypeMirrorFor(Double.class.getCanonicalName()));
		definedTypes.put(UseFloat.class.getName(), getTypeMirrorFor(Float.class.getCanonicalName()));
		definedTypes.put(UseInt.class.getName(), getTypeMirrorFor(Integer.class.getCanonicalName()));
		definedTypes.put(UseLong.class.getName(), getTypeMirrorFor(Long.class.getCanonicalName()));
		definedTypes.put(UseNull.class.getName(), null);
		definedTypes.put(UseShort.class.getName(), getTypeMirrorFor(Short.class.getCanonicalName()));
		definedTypes.put(UseString.class.getName(), getTypeMirrorFor(String.class.getCanonicalName()));
	}

	public TypeMirror getDefinedTypeFor(final AnnotationMirror useAnnotation) {
		final String key = useAnnotation.getAnnotationType().toString();

		if (definedTypes.containsKey(key)) {
			return definedTypes.get(key);
		} else {
			throw new RuntimeException("No defined type found for: " + useAnnotation);
		}
	}

	private TypeMirror getTypeMirrorFor(final String canonicalClassName) {
		return elementUtil.getTypeElement(canonicalClassName).asType();
	}
}