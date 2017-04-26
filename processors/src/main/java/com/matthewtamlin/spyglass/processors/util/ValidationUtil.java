package com.matthewtamlin.spyglass.processors.util;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.meta_annotations.CallHandler;
import com.matthewtamlin.spyglass.annotations.meta_annotations.Default;
import com.matthewtamlin.spyglass.annotations.meta_annotations.Use;
import com.matthewtamlin.spyglass.annotations.meta_annotations.ValueHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Tested(testMethod = "automated")
public class ValidationUtil {
	private static List<MethodRule> methodRules = new ArrayList<>();

	static {
		createMethodRules();
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
					final String message = "Methods must not have multiple handler annotations. " +
							"Check method \"%1$s\".";

					throw new SpyglassValidationException(String.format(message, method));
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
					final String message = "Methods must not have multiple default annotations. " +
							"Check method \"%1$s\".";

					throw new SpyglassValidationException(String.format(message, method));
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
					final String message = "Methods must not have default annotations without " +
							"handler annotations. Check method \"%1$s\".";

					throw new SpyglassValidationException(String.format(message, method));
				}
			}
		});

		// Check for call handlers with defaults
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				final int callHandlerCount = countAnnotations(
						method.getDeclaredAnnotations(),
						CallHandler.class);

				final int defaultCount = countAnnotations(
						method.getDeclaredAnnotations(),
						Default.class);

				if (callHandlerCount == 1 && defaultCount == 1) {
					final String message = "Methods with handlers that pass no value must not " +
							"have default annotations. Check method \"%1$s\".";

					throw new SpyglassValidationException(String.format(message, method));
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
					final String message = "Use-annotations must not be present on methods " +
							"without handler annotations. Check method \"%1$s\".";

					throw new SpyglassValidationException(String.format(message, method));
				}
			}
		});

		// Check parameter count exceeds viable minimum for value handlers
		methodRules.add(new MethodRule() {
			@Override
			public void checkMethodComplies(final Method method) {
				if (countAnnotations(method.getDeclaredAnnotations(), ValueHandler.class) == 1) {
					final int parameterCount = method.getParameterAnnotations().length;

					if (parameterCount < 1) {
						final String message = "An incorrect number of parameters were " +
								"found on method \"%1$s\". Expected at least one.";

						throw new SpyglassValidationException(String.format(message, method));
					}
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
						final String message = "Parameters must not have multiple use-annotations" +
								". Check parameter %1$s of method \"%2$s\".";

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
				if (countAnnotations(method.getDeclaredAnnotations(), ValueHandler.class) == 1) {
					final int parameterCount = method.getParameterAnnotations().length;
					final int expectedUseCount = parameterCount - 1;
					final int actualUseCount = countUseAnnotations(method);

					if (actualUseCount != expectedUseCount) {
						final String message = "Method \"%1$s\" has an incorrect number of " +
								"use-annotations. Expected %2$s but instead found %3$s.";

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
				if (countAnnotations(method.getDeclaredAnnotations(), CallHandler.class) == 1) {
					final int expectedUseCount = method.getParameterAnnotations().length;
					final int actualUseCount = countUseAnnotations(method);

					if (actualUseCount != expectedUseCount) {
						final String message = "Method \"%1$s\" has an incorrect number of " +
								"use-annotations. Expected %2$s but instead found %3$s.";

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

	private interface MethodRule {
		public void checkMethodComplies(Method method) throws SpyglassValidationException;
	}
}