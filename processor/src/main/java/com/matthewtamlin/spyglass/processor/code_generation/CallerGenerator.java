package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.CallHandlerAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.DefaultAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.ValueHandlerAnnoRetriever;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class CallerGenerator {
	private final GetDefaultMethodGenerator getDefaultGenerator;

	private final GetValueMethodGenerator getValueGenerator;

	private final GetPlaceholderMethodGenerator getPlaceholderGenerator;

	private final ValueIsAvailableMethodGenerator valueIsAvailableGenerator;

	private final SpecificValueIsAvailableMethodGenerator specificValueIsAvailableGenerator;

	private CastWrapperGenerator wrapperGenerator;

	public CallerGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		getDefaultGenerator = new GetDefaultMethodGenerator(coreHelpers);
		getValueGenerator = new GetValueMethodGenerator(coreHelpers);
		getPlaceholderGenerator = new GetPlaceholderMethodGenerator(coreHelpers);
		valueIsAvailableGenerator = new ValueIsAvailableMethodGenerator(coreHelpers);
		specificValueIsAvailableGenerator = new SpecificValueIsAvailableMethodGenerator(coreHelpers);
		wrapperGenerator = new CastWrapperGenerator(coreHelpers);
	}

	public TypeSpec generateFor(
			final ExecutableElement method,
			final CodeBlock contextParameter,
			final CodeBlock targetParameter,
			final CodeBlock attrsParameter) {

		checkNotNull(method, "Argument \'method\' cannot be null.");
		checkNotNull(contextParameter, "Argument \'contextParameter\' cannot be null.");
		checkNotNull(targetParameter, "Argument \'targetParameter\' cannot be null.");
		checkNotNull(attrsParameter, "Argument \'attrsParameter\' cannot be null.");

		if (CallHandlerAnnoRetriever.hasAnnotation(method)) {
			return generateForCallHandler(method, contextParameter, targetParameter, attrsParameter);

		} else if (ValueHandlerAnnoRetriever.hasAnnotation(method)) {
			if (DefaultAnnoRetriever.hasAnnotation(method)) {
				return generateForValueHandlerWithDefault(method, contextParameter, targetParameter, attrsParameter);
			} else {
				return generateCallerForValueHandlerWithoutDefault(
						method,
						contextParameter,
						targetParameter,
						attrsParameter);
			}
		} else {
			return null;
		}
	}

	private TypeSpec generateForCallHandler(
			final ExecutableElement e,
			final CodeBlock contextParameter,
			final CodeBlock targetParameter,
			final CodeBlock attrsParameter) {

		final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
				getNameOfTargetClass(e),
				contextParameter,
				targetParameter,
				attrsParameter);

		final CodeBlock.Builder invocationBuilder = CodeBlock
				.builder()
				.add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());

		for (int i = 0; i < e.getParameters().size(); i++) {
			final VariableElement parameter = e.getParameters().get(i);
			final AnnotationMirror useAnno = UseAnnoRetriever.getAnnotation(parameter);
			final MethodSpec argMethod = getPlaceholderGenerator.generateFor(useAnno, i);

			invocationBuilder.add(wrapperGenerator.generateFor(argMethod, parameter.asType()));
			callerBuilder.addMethod(argMethod);

			if (i < e.getParameters().size() - 1) {
				invocationBuilder.add(", ");
			}
		}

		invocationBuilder.add(");");

		final MethodSpec specificValueIsAvailable = specificValueIsAvailableGenerator.generateFor(
				CallHandlerAnnoRetriever.getAnnotation(e));

		final MethodSpec call = CallerDef
				.getNewCallMethodPrototype()
				.addCode(CodeBlock
						.builder()
						.beginControlFlow("if ($N())", specificValueIsAvailable)
						.add(invocationBuilder.build())
						.endControlFlow()
						.build())
				.build();

		return callerBuilder
				.addMethod(specificValueIsAvailable)
				.addMethod(call)
				.build();
	}

	private TypeSpec generateCallerForValueHandlerWithoutDefault(
			final ExecutableElement e,
			final CodeBlock contextParameter,
			final CodeBlock targetParameter,
			final CodeBlock attrsParameter) {

		final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
				getNameOfTargetClass(e),
				contextParameter,
				targetParameter,
				attrsParameter);

		final CodeBlock.Builder invocationBuilder = CodeBlock
				.builder()
				.add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());

		for (int i = 0; i < e.getParameters().size(); i++) {
			final VariableElement parameter = e.getParameters().get(i);

			final MethodSpec argMethod = UseAnnoRetriever.hasAnnotation(parameter) ?
					getPlaceholderGenerator.generateFor(UseAnnoRetriever.getAnnotation(parameter), i) :
					getValueGenerator.generateFor(ValueHandlerAnnoRetriever.getAnnotation(e));

			invocationBuilder.add(wrapperGenerator.generateFor(argMethod, parameter.asType()));
			callerBuilder.addMethod(argMethod);

			if (i < e.getParameters().size() - 1) {
				invocationBuilder.add(", ");
			}
		}

		invocationBuilder.add(");");

		final MethodSpec specificValueIsAvailable = specificValueIsAvailableGenerator.generateFor(
				CallHandlerAnnoRetriever.getAnnotation(e));

		final MethodSpec call = CallerDef
				.getNewCallMethodPrototype()
				.addCode(CodeBlock
						.builder()
						.beginControlFlow("if ($N())", specificValueIsAvailable)
						.add(invocationBuilder.build())
						.endControlFlow()
						.build())
				.build();

		return callerBuilder
				.addMethod(specificValueIsAvailable)
				.addMethod(call)
				.build();
	}

	private TypeSpec generateForValueHandlerWithDefault(
			final ExecutableElement e,
			final CodeBlock contextParameter,
			final CodeBlock targetParameter,
			final CodeBlock attrsParameter) {

		final TypeSpec.Builder callerBuilder = CallerDef.getNewAnonymousCallerPrototype(
				getNameOfTargetClass(e),
				contextParameter,
				targetParameter,
				attrsParameter);

		final CodeBlock.Builder valueAvailableCaseInvocationBuilder = CodeBlock
				.builder()
				.add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());

		final CodeBlock.Builder valueUnavailableCaseInvocationBuilder = CodeBlock
				.builder()
				.add("$N().$N(", CallerDef.GET_TARGET, e.getSimpleName());

		for (int i = 0; i < e.getParameters().size(); i++) {
			final VariableElement parameter = e.getParameters().get(i);

			if (UseAnnoRetriever.hasAnnotation(parameter)) {
				final AnnotationMirror useAnno = UseAnnoRetriever.getAnnotation(parameter);
				final MethodSpec argMethod = getPlaceholderGenerator.generateFor(useAnno, i);

				valueAvailableCaseInvocationBuilder.add(wrapperGenerator.generateFor(argMethod, parameter.asType()));
				valueUnavailableCaseInvocationBuilder.add(wrapperGenerator.generateFor(argMethod, parameter.asType()));

				callerBuilder.addMethod(argMethod);
			} else {
				final AnnotationMirror valueHandlerAnno = ValueHandlerAnnoRetriever.getAnnotation(e);
				final AnnotationMirror defaultAnno = DefaultAnnoRetriever.getAnnotation(e);

				final MethodSpec valueAvailableCaseArgMethod = getValueGenerator.generateFor(valueHandlerAnno);
				final MethodSpec valueUnavailableCaseArgMethod = getDefaultGenerator.generateFor(defaultAnno);

				valueAvailableCaseInvocationBuilder.add(wrapperGenerator.generateFor(
						valueAvailableCaseArgMethod,
						parameter.asType()));

				valueUnavailableCaseInvocationBuilder.add(wrapperGenerator.generateFor(
						valueUnavailableCaseArgMethod,
						parameter.asType()));

				callerBuilder.addMethod(valueAvailableCaseArgMethod);
				callerBuilder.addMethod(valueUnavailableCaseArgMethod);
			}

			if (i < e.getParameters().size() - 1) {
				valueAvailableCaseInvocationBuilder.add(", ");
				valueUnavailableCaseInvocationBuilder.add(", ");
			}
		}

		valueAvailableCaseInvocationBuilder.add(");");
		valueUnavailableCaseInvocationBuilder.add(");");

		final MethodSpec valueIsAvailable = valueIsAvailableGenerator.generateFor(
				ValueHandlerAnnoRetriever.getAnnotation(e));

		final MethodSpec call = CallerDef
				.getNewCallMethodPrototype()
				.addCode(CodeBlock
						.builder()
						.beginControlFlow("if ($N())", valueIsAvailable)
						.add(valueAvailableCaseInvocationBuilder.build())
						.nextControlFlow("else")
						.add(valueUnavailableCaseInvocationBuilder.build())
						.endControlFlow()
						.build())
				.build();

		return callerBuilder
				.addMethod(valueIsAvailable)
				.addMethod(call)
				.build();
	}

	private TypeName getNameOfTargetClass(final ExecutableElement method) {
		final TypeElement enclosingType = (TypeElement) method.getEnclosingElement();
		return TypeName.get(enclosingType.asType());
	}
}