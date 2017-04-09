package com.matthewtamlin.spyglass.library.util;

import com.matthewtamlin.spyglass.library.handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.library.meta_annotations.Default;
import com.matthewtamlin.spyglass.library.meta_annotations.Handler;
import com.matthewtamlin.spyglass.library.meta_annotations.Use;

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

	public static void validateAnnotations(final Field field) throws SpyglassValidationException {
		for (final FieldRule rule : fieldRules) {
			rule.checkFieldComplies(field);
		}
	}

	public static void validateAnnotations(final Method method) throws SpyglassValidationException {
		for (final MethodRule rule : methodRules) {
			rule.checkMethodComplies(method);
		}
	}

	private static void createFieldRules() {
		fieldRules.add(new FieldRule() {
			@Override
			public void checkFieldComplies(final Field field) {
				final Annotation[] annotations = field.getDeclaredAnnotations();
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);

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
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (handlerAnnotationCount == 0 && defaultAnnotationCount > 0) {
					throw new SpyglassValidationException("Field " + field + " has a default " +
							"annotation but no handler annotation.");
				}
			}
		});
	}

	private static void createMethodRules() {
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Annotation[] annotations = method.getDeclaredAnnotations();
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);

				if (handlerAnnotationCount > 1) {
					throw new SpyglassValidationException("Method " + method + " has multiple " +
							"handler annotations.");
				}
			}
		});

		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Annotation[] annotations = method.getDeclaredAnnotations();
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (defaultAnnotationCount > 1) {
					throw new SpyglassValidationException("Method " + method + " has multiple " +
							"default annotations.");
				}
			}
		});

		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Annotation[] annotations = method.getDeclaredAnnotations();
				final int handlerAnnotationCount = countAnnotations(annotations, Handler.class);
				final int defaultAnnotationCount = countAnnotations(annotations, Default.class);

				if (handlerAnnotationCount == 0 && defaultAnnotationCount > 0) {
					throw new SpyglassValidationException("Method " + method + " has a default " +
							"annotation but no handler annotation.");
				}
			}
		});

		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Map<Integer, Set<Annotation>> useAnnotations = getUseAnnotations(method);

				for (final Set<Annotation> annotationsOnParameter : useAnnotations.values()) {
					if (annotationsOnParameter.size() > 1) {
						throw new SpyglassValidationException("A parameter for method " + method
								+ " has multiple use annotations.");
					}
				}
			}
		});

		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final int parameterCount = method.getParameterAnnotations().length;

				// Expect one additional use annotation if the EnumConstantHandler is present
				final int expectedUseAnnotationCount =
						method.isAnnotationPresent(EnumConstantHandler.class) ?
								parameterCount :
								parameterCount - 1;

				final Map<Integer, Set<Annotation>> useAnnotations = getUseAnnotations(method);

				int useAnnotationCount = 0;

				for (final Set<Annotation> annotationsOnParameter : useAnnotations.values()) {
					if (!annotationsOnParameter.isEmpty()) {
						useAnnotationCount++;
					}
				}

				if (useAnnotationCount != expectedUseAnnotationCount) {
					final String message = "Method %1$s has an incorrect number of Use " +
							"annotations. Expected %2$s but instead found %3$s.";

					final String formattedMessage = String.format(
							message,
							method,
							expectedUseAnnotationCount,
							useAnnotationCount);

					throw new SpyglassValidationException(formattedMessage);
				}
			}
		});
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

	private interface FieldRule {
		public void checkFieldComplies(Field field);
	}

	private interface MethodRule {
		public void checkMethodComplies(Method method);
	}
}