package com.matthewtamlin.spyglass.processor.validation;

import com.google.common.collect.ImmutableList;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.DefaultAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.ValueHandlerAnnoRetriever;
import com.matthewtamlin.spyglass.processor.code_generation.GetDefaultMethodGenerator;
import com.matthewtamlin.spyglass.processor.code_generation.GetPlaceholderMethodGenerator;
import com.matthewtamlin.spyglass.processor.code_generation.GetValueMethodGenerator;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.mirror_helpers.TypeMirrorHelper;
import com.squareup.javapoet.MethodSpec;

import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class TypeValidator implements Validator {
	private final List<Rule> rules = ImmutableList.of(
			// Every value handler annotation must be applicable to the annotated method
			new Rule() {
				@Override
				public Result checkElement(final ExecutableElement element) {
					if (!ValueHandlerAnnoRetriever.hasAnnotation(element)) {
						return Result.createSuccessful();
					}

					final AnnotationMirror anno = ValueHandlerAnnoRetriever.getAnnotation(element);
					final String annoName = anno.getAnnotationType().toString();

					final MethodSpec supplier = getValueMethodGenerator.generateFor(anno);
					final TypeMirror suppliedType = returnTypeToTypeMirror(supplier);
					final TypeMirror recipientType = getParameterWithoutUseAnnotation(element).asType();

					// A limitation currently prevents enums from being checked at compile time
					if (annoName.equals(EnumConstantHandler.class.getName())) {
						return Result.createSuccessful();
					}

					if (!isAssignableOrConvertible(suppliedType, recipientType)) {
						return Result.createFailure(
								"Misused handler annotation found. \'%1$s\' cannot be cast to \'%2$s\'.",
								suppliedType,
								recipientType);
					}

					return Result.createSuccessful();
				}
			},

			// Every default annotation must be applicable to the annotated method
			new Rule() {
				@Override
				public Result checkElement(final ExecutableElement element) {
					if (!DefaultAnnoRetriever.hasAnnotation(element)) {
						return Result.createSuccessful();
					}

					final AnnotationMirror anno = DefaultAnnoRetriever.getAnnotation(element);
					final String annoName = anno.getAnnotationType().toString();

					final MethodSpec supplier = getDefaultMethodGenerator.generateFor(anno);
					final TypeMirror suppliedType = returnTypeToTypeMirror(supplier);
					final TypeMirror recipientType = getParameterWithoutUseAnnotation(element).asType();

					// A limitation currently prevents enums from being checked at compile time
					if (annoName.equals(DefaultToEnumConstant.class.getName())) {
						return Result.createSuccessful();
					}

					if (annoName.equals(DefaultToNull.class.getName())) {
						if (typeMirrorHelper.isPrimitive(recipientType)) {
							return Result.createFailure(
									"Misused default annotation found. Primitive parameters cannot receive null.");

						} else {
							return Result.createSuccessful();
						}
					}

					if (!isAssignableOrConvertible(suppliedType, recipientType)) {
						return Result.createFailure(
								"Misused default annotation found. \'%1$s\' cannot be cast to \'%2$s\'.",
								suppliedType,
								recipientType);
					}

					return Result.createSuccessful();
				}
			},

			// Every use annotation must be applicable to the annotated parameter
			new Rule() {
				@Override
				public Result checkElement(final ExecutableElement element) {
					for (final VariableElement parameter : element.getParameters()) {
						if (UseAnnoRetriever.hasAnnotation(parameter)) {
							final Result result = checkParameter(parameter);

							if (!result.isSuccessful()) {
								return result;
							}
						}
					}

					return Result.createSuccessful();
				}

				private Result checkParameter(final VariableElement parameter) {
					final AnnotationMirror anno = UseAnnoRetriever.getAnnotation(parameter);
					final String annoName = anno.getAnnotationType().toString();

					final MethodSpec supplier = getPlaceholderMethodGenerator.generateFor(anno, 0);
					final TypeMirror suppliedType = returnTypeToTypeMirror(supplier);
					final TypeMirror recipientType = parameter.asType();

					if (annoName.equals(UseNull.class.getName())) {
						if (typeMirrorHelper.isPrimitive(recipientType)) {
							return Result.createFailure(
									"Misused use annotation found. Primitive arguments cannot receive null.");

						} else {
							return Result.createSuccessful();
						}
					}

					if (!isAssignableOrConvertible(suppliedType, recipientType)) {
						return Result.createFailure(
								"Misused use annotation found. \'%1$s\' cannot be cast to \'%2$s\'.",
								suppliedType,
								recipientType);
					}

					return Result.createSuccessful();
				}
			});

	private Elements elementHelper;

	private Types typeHelper;

	private TypeMirrorHelper typeMirrorHelper;

	private GetValueMethodGenerator getValueMethodGenerator;

	private GetDefaultMethodGenerator getDefaultMethodGenerator;

	private GetPlaceholderMethodGenerator getPlaceholderMethodGenerator;

	public TypeValidator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		elementHelper = coreHelpers.getElementHelper();
		typeHelper = coreHelpers.getTypeHelper();
		typeMirrorHelper = coreHelpers.getTypeMirrorHelper();

		getValueMethodGenerator = new GetValueMethodGenerator(coreHelpers);
		getDefaultMethodGenerator = new GetDefaultMethodGenerator(coreHelpers);
		getPlaceholderMethodGenerator = new GetPlaceholderMethodGenerator(coreHelpers);
	}

	public Result validate(final ExecutableElement element) {
		for (final Rule rule : rules) {
			final Result result = rule.checkElement(element);

			if (!result.isSuccessful()) {
				return result;
			}
		}

		return Result.createSuccessful();
	}

	private TypeMirror returnTypeToTypeMirror(final MethodSpec methodSpec) {
		return elementHelper.getTypeElement(methodSpec.returnType.toString()).asType();
	}

	private static VariableElement getParameterWithoutUseAnnotation(final ExecutableElement method) {
		for (final VariableElement parameter : method.getParameters()) {
			if (!UseAnnoRetriever.hasAnnotation(parameter)) {
				return parameter;
			}
		}

		return null;
	}

	private boolean isAssignableOrConvertible(final TypeMirror suppliedType, final TypeMirror recipientType) {
		return typeHelper.isAssignable(suppliedType, recipientType) ||
				(typeMirrorHelper.isNumber(suppliedType) && typeMirrorHelper.isNumber(recipientType)) ||
				(typeMirrorHelper.isCharacter(suppliedType) && typeMirrorHelper.isCharacter(recipientType));
	}

	private interface Rule {
		public Result checkElement(ExecutableElement element);
	}
}