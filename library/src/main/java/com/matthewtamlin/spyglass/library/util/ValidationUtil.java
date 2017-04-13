package com.matthewtamlin.spyglass.library.util;

import com.matthewtamlin.spyglass.library.meta_annotations.CallHandler;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Use;
import com.matthewtamlin.spyglass.library.meta_annotations.ValueHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ValidationUtil {
	private static List<FieldRule> fieldRules = new ArrayList<>();

	private static List<MethodRule> methodRules = new ArrayList<>();

	static {
		createFieldRules();
		createMethodRules();
	}

	private static void createFieldRules() {
		fieldRules.add(new FieldRule() {
			@Override
			public void checkFieldComplies(final Field field) {
				final Annotation[] annotations = field.getDeclaredAnnotations();
				final int handlerAnnotationCount =
						countAnnotations(annotations, ValueHandler.class);

				if (handlerAnnotationCount > 1) {
					throw new SpyglassValidationException("Field " + field + " has multiple " +
							"handler annotations.");
				}
			}
		});

		fieldRules.add(new FieldRule() {
			@Override
			public void checkFieldComplies(final Field field) {
				final Annotation[] annotations = field.getDeclaredAnnotations();
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (defaultAnnotationCount > 1) {
					throw new SpyglassValidationException("Field " + field + " has multiple " +
							"default annotations.");
				}
			}
		});

		fieldRules.add(new FieldRule() {
			@Override
			public void checkFieldComplies(final Field field) {
				final Annotation[] annotations = field.getDeclaredAnnotations();
				final int handlerAnnotationCount =
						countAnnotations(annotations, ValueHandler.class);
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (handlerAnnotationCount == 0 && defaultAnnotationCount > 0) {
					throw new SpyglassValidationException("Field " + field + " has a default " +
							"annotation but no handler annotation.");
				}
			}
		});
	}

	private static void createMethodRules() {

	}

	public static void validateField(final Field field) throws SpyglassValidationException {
		for (final FieldRule rule : fieldRules) {
			rule.checkFieldComplies(field);
		}
	}

	public static void validateMethod(final Method method) throws SpyglassValidationException {
		for (final MethodRule rule : methodRules) {
			rule.checkMethodComplies(method);
		}
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

	@SafeVarargs
	private static int countAnnotations(
			final Annotation[] annotations,
			final Class<? extends Annotation>... metaAnnotations) {

		int count = 0;

		for (Class<? extends Annotation> metaAnnotation : metaAnnotations) {
			count += countAnnotations(annotations, metaAnnotation);
		}

		return count;
	}

	private static Map<Integer, Set<Annotation>> getUseAnnotations(final Method method) {
		final Map<Integer, Set<Annotation>> useAnnotationsByParameterIndex = new HashMap<>();

		final Annotation[][] parameterAnnotations = method.getParameterAnnotations();

		for (int i = 0; i < parameterAnnotations.length; i++) {
			useAnnotationsByParameterIndex.put(i, new HashSet<Annotation>());

			for (final Annotation a : parameterAnnotations[i]) {
				if (a.annotationType().isAnnotationPresent(Use.class)) {
					useAnnotationsByParameterIndex.get(i).add(a);
				}
			}
		}

		return useAnnotationsByParameterIndex;
	}

	private static int countUseAnnotations(final Method method) {
		int count = 0;

		for (final Set<Annotation> annotation : getUseAnnotations(method).values()) {
			if (!annotation.isEmpty()) {
				count++;
			}
		}

		return count;
	}

	private interface FieldRule {
		public void checkFieldComplies(Field field) throws SpyglassValidationException;
	}

	private interface MethodRule {
		public void checkMethodComplies(Method method) throws SpyglassValidationException;
	}
}