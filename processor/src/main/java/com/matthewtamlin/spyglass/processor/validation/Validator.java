package com.matthewtamlin.spyglass.processor.validation;


import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.CallHandlerAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.DefaultAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.ValueHandlerAnnoRetriever;
import com.matthewtamlin.spyglass.processor.code_generation.GetArgumentGenerator;
import com.matthewtamlin.spyglass.processor.core.AnnotationRegistry;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.mirror_helpers.TypeMirrorHelper;
import com.squareup.javapoet.MethodSpec;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.Modifier.PRIVATE;
import static javax.lang.model.element.Modifier.STATIC;

@Tested(testMethod = "automated")
public class Validator {
	private final List<Rule> rules = new ArrayList<>();

	{
		// Every element must have no more than one handler annotation
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				if (countCombinedHandlerAnnotations(element) > 1) {
					final String message = "Methods must not have multiple handler annotations.";
					throw new ValidationException(message);
				}
			}
		});

		// Every element must have no more than one default annotation
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				if (countDefaultAnnotations(element) > 1) {
					final String message = "Methods must not have multiple default annotations.";
					throw new ValidationException(message);
				}
			}
		});

		// Every element with a default annotation must also have a value handler annotation
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				if (DefaultAnnoRetriever.hasAnnotation(element) && !ValueHandlerAnnoRetriever.hasAnnotation(element)) {
					final String message = "Methods without handler annotations must not have default annotations.";
					throw new ValidationException(message);
				}
			}
		});

		// Every element with a default annotation must not have a call handler annotation
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				if (DefaultAnnoRetriever.hasAnnotation(element) && CallHandlerAnnoRetriever.hasAnnotation(element)) {
					final String message = "Methods with handlers annotations that pass no value must not have " +
							"default annotations.";

					throw new ValidationException(message);
				}
			}
		});

		// Every element with a value handle annotation must have at least one parameter
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				final int parameterCount = ((ExecutableElement) element).getParameters().size();

				if (ValueHandlerAnnoRetriever.hasAnnotation(element) && parameterCount < 1) {
					final String message = "Methods with handler annotations that pass a value must have at least" +
							" one parameter.";

					throw new ValidationException(message);
				}
			}
		});

		// Every parameter must have at most one use annotations
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				final Map<Integer, Set<Annotation>> useAnnotations = getUseAnnotations(element);

				for (final Integer paramIndex : useAnnotations.keySet()) {
					if (useAnnotations.get(paramIndex).size() > 1) {
						final String message = "Parameters must not have multiple use annotations.";
						throw new ValidationException(message);
					}
				}
			}
		});

		// Every element with a value handler must have a use annotation on every parameter except one
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				final int paramCount = ((ExecutableElement) element).getParameters().size();
				final int annotatedParamCount = countNonEmptySets(getUseAnnotations(element).values());

				if (ValueHandlerAnnoRetriever.hasAnnotation(element) && annotatedParamCount != paramCount - 1) {
					final String message = "Methods with handler annotations which pass a value must have use " +
							"annotations on every parameter except one.";

					throw new ValidationException(message);
				}
			}
		});

		// Every element with a call handler must have a use annotation on every parameter
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				final int paramCount = ((ExecutableElement) element).getParameters().size();
				final int annotatedParamCount = countNonEmptySets(getUseAnnotations(element).values());

				if (CallHandlerAnnoRetriever.hasAnnotation(element) && annotatedParamCount != paramCount) {
					final String message = "Methods with handler annotations which pass no value must have " +
							"use annotations on every parameter.";

					throw new ValidationException(message);
				}
			}
		});

		// Every element must be public, protected or package-private
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				if (element.getModifiers().contains(PRIVATE)) {
					throw new ValidationException("Methods with handler annotations must have public, protected, or " +
							"default access. Private methods are not compatible with the Spyglass Framework.");
				}
			}
		});

		// Every element must belong to a class which can be statically instantiated
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				if (!checkParentsRecursively(element)) {
					throw new ValidationException("Methods with handler annotations must be accessible from static " +
							"context.");
				}
			}

			//TODO need to look into making this method better
			private boolean checkParentsRecursively(final Element element) {
				final TypeElement parent = (TypeElement) element.getEnclosingElement();

				if (parent == null) {
					return true;
				}

				switch (parent.getNestingKind()) {
					case TOP_LEVEL:
						return true;
					case MEMBER:
						return parent.getModifiers().contains(STATIC);
					case LOCAL:
						return false;
					case ANONYMOUS:
						return false;
				}

				throw new RuntimeException("Should never get here.");
			}
		});

		// The use annotations of every element must be applicable to the annotated parameters
		rules.add(new Rule() {
			@Override
			public void checkElement(final ExecutableElement element) throws ValidationException {
				for (final VariableElement parameter : ((ExecutableElement) element).getParameters()) {
					if (UseAnnoRetriever.hasAnnotation(parameter)) {
						final boolean paramHasUseNullAnno = UseAnnoRetriever
								.getAnnotation(parameter)
								.getAnnotationType()
								.toString()
								.equals(UseNull.class.getName());

						if (paramHasUseNullAnno) {
							checkUseNullCase(parameter);
						} else {
							checkGeneralCase(parameter);
						}
					}
				}
			}

			private void checkUseNullCase(final VariableElement parameter) throws ValidationException {
				if (typeMirrorHelper.isPrimitive(parameter.asType())) {
					throw new ValidationException("UseNull annotations cannot be applied to primitive parameters.");
				}
			}

			private void checkGeneralCase(final VariableElement parameter) throws ValidationException {
				final TypeMirror recipientType = parameter.asType();

				final AnnotationMirror useAnno = UseAnnoRetriever.getAnnotation(parameter);

				final GetArgumentGenerator generator = new GetArgumentGenerator(coreHelpers);
				final MethodSpec suppliedMethod = generator.generateFor(useAnno, 0);

				final TypeMirror suppliedType = elementUtil
						.getTypeElement(suppliedMethod.returnType.toString())
						.asType();

				if (typeMirrorHelper.isNumber(suppliedType)) {
					if (!typeMirrorHelper.isNumber(recipientType) &&
							!typeMirrorHelper.isCharacter(recipientType) &&
							!typeMirrorHelper.isAssignable(suppliedType, recipientType)) {

						throw new ValidationException("A use annotation was applied to a parameter incorrectly.");
					}
				} else if (typeMirrorHelper.isBoolean(suppliedType)) {
					if (!typeMirrorHelper.isBoolean(recipientType) &&
							!typeMirrorHelper.isAssignable(suppliedType, recipientType)) {

						throw new ValidationException("A use annotation was incorrectly to a parameter incorrectly.");
					}
				} else {
					if (!typeMirrorHelper.isAssignable(suppliedType, recipientType)) {
						throw new ValidationException("A use annotation was incorrectly to a parameter incorrectly.");
					}
				}
			}
		});
	}

	private CoreHelpers coreHelpers;

	private Elements elementUtil;

	private TypeMirrorHelper typeMirrorHelper;

	public Validator(final CoreHelpers coreHelpers) {
		this.coreHelpers = checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");
		this.elementUtil = coreHelpers.getElementHelper();
		this.typeMirrorHelper = coreHelpers.getTypeMirrorHelper();
	}

	public void validateElement(final ExecutableElement element) throws ValidationException {
		for (final Rule rule : rules) {
			rule.checkElement(element);
		}
	}

	private static int countCallHandlerAnnotations(final Element e) {
		int count = 0;

		for (final Class<? extends Annotation> annotation : AnnotationRegistry.CALL_HANDLER_ANNOS) {
			if (e.getAnnotation(annotation) != null) {
				count++;
			}
		}

		return count;
	}

	private static int countValueHandlerAnnotations(final Element e) {
		int count = 0;

		for (final Class<? extends Annotation> annotation : AnnotationRegistry.VALUE_HANDLER_ANNOS) {
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

		for (final Class<? extends Annotation> annotation : AnnotationRegistry.DEFAULT_ANNOS) {
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

			for (final Class<? extends Annotation> annotation : AnnotationRegistry.USE_ANNOS) {
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
		public void checkElement(ExecutableElement element) throws ValidationException;
	}
}