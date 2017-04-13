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
		// Check for multiple handler annotations
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

		// Check for multiple default annotations
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

		// Check for a default annotation without a handler annotation
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
		// Check for multiple handler annotations
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final int handlerCount = countAnnotations(
						method.getDeclaredAnnotations(),
						ValueHandler.class,
						CallHandler.class);

				if (handlerCount > 1) {
					throw new SpyglassValidationException("Method " + method + " has multiple " +
							"handler annotations.");
				}
			}
		});

		// Check for multiple default annotations
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final int defaultCount = countAnnotations(
						method.getDeclaredAnnotations(),
						Default.class);

				if (defaultCount > 1) {
					throw new SpyglassValidationException("Method " + method + " has multiple " +
							"default annotations.");
				}
			}
		});

		// Check for a default annotation without a handler annotation
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final int handlerCount = countAnnotations(
						method.getDeclaredAnnotations(),
						ValueHandler.class,
						CallHandler.class);

				final int defaultCount = countAnnotations(
						method.getDeclaredAnnotations(),
						Default.class);

				if (handlerCount == 0 && defaultCount == 1) {
					throw new SpyglassValidationException("Method " + method + " has a default " +
							"annotation but no handler annotation.");
				}
			}
		});

		// Check for use annotations without a handler annotation
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final int handlerCount = countAnnotations(
						method.getDeclaredAnnotations(),
						ValueHandler.class,
						CallHandler.class);

				final int useCount = countUseAnnotations(method);

				if (handlerCount == 0 && useCount > 0) {
					throw new SpyglassValidationException("Method " + method + " has Use " +
							"annotations but no handler annotation.");
				}
			}
		});

		// Check parameter count exceeds viable minimum
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final int minimumViableParameterCount =
						method.isAnnotationPresent(ValueHandler.class) ? 1 : 0;

				final int actualParameterCount = method.getParameterAnnotations().length;

				if (actualParameterCount < minimumViableParameterCount) {
					final String message = "Method %1$s has an insufficient number of parameters." +
							" Expect at least %2$s but found %3$s.";

					throw new SpyglassValidationException(String.format(message,
							method,
							minimumViableParameterCount,
							actualParameterCount));
				}
			}
		});

		// Check for parameters with multiple use annotations
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final Map<Integer, Set<Annotation>> useAnnotations = getUseAnnotations(method);

				for (final int parameterIndex : useAnnotations.keySet()) {
					if (useAnnotations.get(parameterIndex).size() > 1) {
						final String message = "Parameter %1$s of method %2$s has multiple use " +
								"annotations.";

						throw new SpyglassValidationException(String.format(message,
								parameterIndex,
								method));
					}
				}
			}
		});

		// Check correct number of parameters have use annotations (value handlers case)
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				if (method.isAnnotationPresent(ValueHandler.class)) {
					final int parameterCount = method.getParameterAnnotations().length;
					final int expectedUseCount = parameterCount - 1;
					final int actualUseCount = countUseAnnotations(method);

					if (actualUseCount != expectedUseCount) {
						final String message = "Method %1$s has an incorrect number of Use " +
								"annotations. Expected %2$s but instead found %3$s.";

						throw new SpyglassValidationException(String.format(
								message,
								method,
								expectedUseCount,
								actualUseCount));
					}
				}
			}
		});

		// Check correct number of parameters have use annotations (call handlers case)
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				if (method.isAnnotationPresent(CallHandler.class)) {
					final int expectedUseCount = method.getParameterAnnotations().length;
					final int actualUseCount = countUseAnnotations(method);

					if (actualUseCount != expectedUseCount) {
						final String message = "Method %1$s has an incorrect number of Use " +
								"annotations. Expected %2$s but instead found %3$s.";

						throw new SpyglassValidationException(String.format(
								message,
								method,
								expectedUseCount,
								actualUseCount));
					}
				}
			}
		});
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