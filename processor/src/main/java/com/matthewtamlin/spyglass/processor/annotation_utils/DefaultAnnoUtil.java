package com.matthewtamlin.spyglass.processor.annotation_utils;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorUtil;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processor.util.SetUtil.unmodifiableSetOf;

@Tested(testMethod = "automated")
public class DefaultAnnoUtil {
	public static AnnotationMirror getAnnotation(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		for (final Class<? extends Annotation> annotationClass : getClasses()) {
			final AnnotationMirror mirror = AnnotationMirrorUtil.getAnnotationMirror(element, annotationClass);

			if (mirror != null) {
				return mirror;
			}
		}

		return null;
	}

	public static boolean hasAnnotation(final ExecutableElement element) {
		return getAnnotation(element) != null;
	}

	public static Set<Class<? extends Annotation>> getClasses() {
		return unmodifiableSetOf(
				DefaultToBoolean.class,
				DefaultToBooleanResource.class,
				DefaultToColorResource.class,
				DefaultToColorStateListResource.class,
				DefaultToDimension.class,
				DefaultToDimensionResource.class,
				DefaultToDrawableResource.class,
				DefaultToEnumConstant.class,
				DefaultToFloat.class,
				DefaultToFractionResource.class,
				DefaultToInteger.class,
				DefaultToIntegerResource.class,
				DefaultToNull.class,
				DefaultToString.class,
				DefaultToStringResource.class,
				DefaultToTextArrayResource.class,
				DefaultToTextResource.class);
	}

	private DefaultAnnoUtil() {
		throw new RuntimeException("Utility class. Do not instantiate.");
	}
}