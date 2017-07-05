package com.matthewtamlin.spyglass.processor.validation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.util.TypeMirrorHelper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.NestingKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processor.core.AnnotationRegistry.CALL_HANDLER_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processor.core.AnnotationRegistry.DEFAULT_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processor.core.AnnotationRegistry.USE_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processor.core.AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

@Tested(testMethod = "automated")
public class Validator {
	private static final List<Rule> rules = new ArrayList<>();

	static {
		createMethodRules();
	}

	private static void createMethodRules() {
		// Check element is a method
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (element.getKind() != ElementKind.METHOD) {
					final String message = "Spyglass annotations must only be applied to methods.";
					throw new ValidationException(message);
				}
			}
		});

		// Check for multiple handler annotations
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (countCombinedHandlerAnnotations(element) > 1) {
					final String message = "Methods must not have multiple handler annotations.";
					throw new ValidationException(message);
				}
			}
		});

		// Check for multiple default annotations
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (countDefaultAnnotations(element) > 1) {
					final String message = "Methods must not have multiple default annotations.";
					throw new ValidationException(message);
				}
			}
		});

		// Check for a default annotation without a handler annotation
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				final int handlerCount = countCombinedHandlerAnnotations(element);
				final int defaultCount = countDefaultAnnotations(element);

				if (handlerCount == 0 && defaultCount == 1) {
					final String message = "Methods without handler annotations must not have default annotations.";
					throw new ValidationException(message);
				}
			}
		});

		// Check for call handlers with defaults
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				final int callHandlerCount = countCallHandlerAnnotations(element);
				final int defaultCount = countDefaultAnnotations(element);

				if (callHandlerCount == 1 && defaultCount == 1) {
					final String message = "Methods with handlers annotations that pass no value must not have " +
							"default annotations.";

					throw new ValidationException(message);
				}
			}
		});

		// Check parameter count exceeds 1 minimum for value handlers
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (countValueHandlerAnnotations(element) == 1) {
					final int paramCount = ((ExecutableElement) element).getParameters().size();

					if (paramCount < 1) {
						final String message = "Methods with handler annotations that pass a value must have at least" +
								" one parameter.";

						throw new ValidationException(message);
					}
				}
			}
		});

		// Check for parameters with multiple use annotations
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				final Map<Integer, Set<Annotation>> useAnnotations = getUseAnnotations(
						(ExecutableElement) element);

				for (final Integer paramIndex : useAnnotations.keySet()) {
					if (useAnnotations.get(paramIndex).size() > 1) {
						final String message = "Parameters must not have multiple use annotations.";
						throw new ValidationException(message);
					}
				}
			}
		});

		// Check correct number of parameters have use annotations (value handlers case)
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (countValueHandlerAnnotations(element) == 1) {
					final int paramCount = ((ExecutableElement) element).getParameters().size();
					final int annotatedParamCount = countNonEmptySets(
							getUseAnnotations((ExecutableElement) element).values());

					if (annotatedParamCount != paramCount - 1) {
						final String message = "Methods with handler annotations which pass a value must have use " +
								"annotations on every parameter except one.";

						throw new ValidationException(message);
					}
				}
			}
		});

		// Check correct number of parameters have use annotations (call handlers case)
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (countCallHandlerAnnotations(element) == 1) {
					final int paramCount = ((ExecutableElement) element).getParameters().size();
					final int annotatedParamCount = countNonEmptySets(
							getUseAnnotations((ExecutableElement) element).values());

					if (annotatedParamCount != paramCount) {
						final String message = "Methods with handler annotations which pass no value must have " +
								"use annotations on every parameter.";

						throw new ValidationException(message);
					}
				}
			}
		});

		// Check correct modifiers are applied to annotation methods
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (element.getModifiers().contains(PRIVATE)) {
					throw new ValidationException("Methods with handler annotations must have public, protected, or " +
							"default access. Private methods are not compatible with the Spyglass Framework.");
				}
			}
		});

		// Check for methods which are members of non-static inner classes (recursively)
		rules.add(new Rule() {
			@Override
			public void checkElementComplies(final Element element) throws ValidationException {
				if (!checkParentsRecursively(element)) {
					throw new ValidationException("Methods with handler annotations must be accessible from static " +
							"context.");
				}
			}

			private boolean checkParentsRecursively(final Element element) {
				final TypeElement parent = (TypeElement) element.getEnclosingElement();

				if (parent == null) {
					// Input was a top level class
					return true;

				} else if (parent.getNestingKind() == NestingKind.TOP_LEVEL) {
					return true;

				} else if (parent.getNestingKind() == NestingKind.MEMBER) {
					// The enclosing element must be static, and its parents must also be valid
					return parent.getModifiers().contains(STATIC);

				} else if (parent.getNestingKind() == NestingKind.LOCAL) {
					return false;

				} else if (parent.getNestingKind() == NestingKind.ANONYMOUS) {
					return false;

				} else {
					throw new RuntimeException("This should never happen.");
				}
			}
		});
	}

	private TypeMirrorHelper typeMirrorHelper;

	public Validator(final TypeMirrorHelper typeMirrorHelper) {
		this.typeMirrorHelper = checkNotNull(typeMirrorHelper, "Argument \'typeMirrorHelper\' cannot be null.");
	}

	public void validateElement(final Element element) throws ValidationException {
		for (final Rule rule : rules) {
			rule.checkElementComplies(element);
		}
	}

	private static int countCallHandlerAnnotations(final Element e) {
		int count = 0;

		for (final Class<? extends Annotation> annotation : CALL_HANDLER_ANNOTATIONS) {
			if (e.getAnnotation(annotation) != null) {
				count++;
			}
		}

		return count;
	}

	private static int countValueHandlerAnnotations(final Element e) {
		int count = 0;

		for (final Class<? extends Annotation> annotation : VALUE_HANDLER_ANNOTATIONS) {
			if (e.getAnnotation(annotation) != null) {
				count++;
			}
		}

		return count;
	}

	private static int countCombinedHandlerAnnotations(final Element e) {
		return countCallHandlerAnnotations(e) + countValueHandlerAnnotations(e);
	}

	private static int countDefaultAnnotations(final Element e) {
		int count = 0;

		for (final Class<? extends Annotation> annotation : DEFAULT_ANNOTATIONS) {
			if (e.getAnnotation(annotation) != null) {
				count++;
			}
		}

		return count;
	}

	private static Map<Integer, Set<Annotation>> getUseAnnotations(
			final ExecutableElement element) {

		final Map<Integer, Set<Annotation>> useAnnotations = new HashMap<>();

		final List<? extends VariableElement> params = element.getParameters();

		for (int i = 0; i < params.size(); i++) {
			useAnnotations.put(i, new HashSet<Annotation>());

			for (final Class<? extends Annotation> annotation : USE_ANNOTATIONS) {
				final Annotation foundAnnotation = params.get(i).getAnnotation(annotation);

				if (foundAnnotation != null) {
					useAnnotations.get(i).add(foundAnnotation);
				}
			}
		}

		return useAnnotations;
	}

	private static int countNonEmptySets(final Collection<? extends Set> collection) {
		int count = 0;

		for (final Set<?> s : collection) {
			if (!s.isEmpty()) {
				count++;
			}
		}

		return count;
	}

	private interface Rule {
		public void checkElementComplies(Element element) throws ValidationException;
	}

	private Validator() {
		throw new RuntimeException("Util class. Do not instantiate.");
	}
}