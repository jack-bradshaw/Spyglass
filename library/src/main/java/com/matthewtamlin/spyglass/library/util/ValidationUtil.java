package com.matthewtamlin.spyglass.library.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ValidationUtil {
	private static Set<FieldRule> fieldRules = new HashSet<>();

	private static Set<MethodRule> methodRules = new HashSet<>();

	static {
		createFieldRules();
		createMethodRules();
	}

	public static void validateAnnotations(final Field field) throws SpyglassValidationException {
		for (final FieldRule rule : fieldRules) {
			rule.checkField(field);
		}
	}

	public static void validateAnnotations(final Method method) throws SpyglassValidationException {
		for (final MethodRule rule : methodRules) {
			rule.checkMethod(method);
		}
	}

	private static void createFieldRules() {

	}

	private static void createMethodRules() {

	}

	private static int countAnnotations(
			final Annotation[] annotations,
			final Class<? extends Annotation> metaAnnotation) {

		int count = 0;

		for (final Annotation a : annotations) {
			if (a.annotationType().isAnnotationPresent(metaAnnotation)) {
				count++;
			}
		}

		return count;
	}

	private interface FieldRule {
		public void checkField(Field field);
	}

	private interface MethodRule {
		public void checkMethod(Method method);
	}
}