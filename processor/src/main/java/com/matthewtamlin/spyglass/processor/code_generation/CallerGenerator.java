package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.CallHandlerAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.DefaultAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.UseAnnoRetriever;
import com.matthewtamlin.spyglass.processor.annotation_retrievers.ValueHandlerAnnoRetriever;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class CallerGenerator {
	private final GetDefaultMethodGenerator getDefaultMethodGenerator;

	private final GetValueMethodGenerator getValueMethodGenerator;

	private final ValueIsAvailableMethodGenerator valueIsAvailableMethodGenerator;

	private final SpecificValueIsAvailableMethodGenerator specificValueIsAvailableMethodGenerator;

	private final DoInvocationGenerator doInvocationGenerator;

	public CallerGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		getDefaultMethodGenerator = new GetDefaultMethodGenerator(coreHelpers);
		getValueMethodGenerator = new GetValueMethodGenerator(coreHelpers);
		valueIsAvailableMethodGenerator = new ValueIsAvailableMethodGenerator(coreHelpers);
		specificValueIsAvailableMethodGenerator = new SpecificValueIsAvailableMethodGenerator(coreHelpers);
		doInvocationGenerator = new DoInvocationGenerator(coreHelpers);
	}

	public TypeSpec generateCaller(final ExecutableElement method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		if (CallHandlerAnnoRetriever.hasAnnotation(method)) {
			return generateCallHandlerCaller(method);

		} else if (ValueHandlerAnnoRetriever.hasAnnotation(method)) {
			return DefaultAnnoRetriever.hasAnnotation(method) ?
					generateValueHandlerCallerWithDefault(method) :
					generateValueHandlerCallerWithoutDefault(method);

		} else {
			throw new IllegalArgumentException("Argument \'method\' has neither a value handler annotation nor a call" +
					" handler annotation.");
		}
	}

	private TypeSpec generateCallHandlerCaller(final ExecutableElement e) {
		final AnnotationMirror callHandlerAnno = CallHandlerAnnoRetriever.getAnnotation(e);

		final MethodSpec specificValueIsAvailable = specificValueIsAvailableMethodGenerator.getMethod(callHandlerAnno);
		final MethodSpec doInvocation = doInvocationGenerator.getMethod(e);

		final MethodSpec call = CallerDef.CALL
				.toBuilder()
				.addCode(CodeBlock
						.builder()
						.beginControlFlow("if ($N(attrs))", specificValueIsAvailable)
						.addStatement("$N(target)", doInvocation)
						.endControlFlow()
						.build())
				.build();

		return getEmptyAnonymousCaller(getNameOfTargetClass(e))
				.addMethod(call)
				.addMethod(specificValueIsAvailable)
				.addMethod(doInvocation)
				.build();
	}

	private TypeSpec generateValueHandlerCallerWithoutDefault(final ExecutableElement e) {
		final AnnotationMirror valueHandlerAnno = ValueHandlerAnnoRetriever.getAnnotation(e);

		final MethodSpec valueIsAvailable = valueIsAvailableMethodGenerator.getMethod(valueHandlerAnno);
		final MethodSpec getValue = getValueMethodGenerator.generateFor(valueHandlerAnno);
		final MethodSpec doInvocation = doInvocationGenerator.getMethod(e);

		final MethodSpec call = CallerDef.getNewCallMethodPrototype()
				.addCode(CodeBlock
						.builder()
						.beginControlFlow("if ($N(attrs))", valueIsAvailable)
						.addStatement("final Object value = $N(attrs)", getValue)
						.addStatement("$N(target, value)", doInvocation)
						.endControlFlow()
						.build())
				.build();

		return getEmptyAnonymousCaller(getNameOfTargetClass(e))
				.addMethod(call)
				.addMethod(valueIsAvailable)
				.addMethod(getValue)
				.addMethod(doInvocation)
				.build();
	}

	private TypeSpec generateValueHandlerCallerWithDefault(final ExecutableElement e) {
		final AnnotationMirror valueHandler = ValueHandlerAnnoRetriever.getAnnotation(e);
		final AnnotationMirror defaultAnno = DefaultAnnoRetriever.getAnnotation(e);

		final MethodSpec valueIsAvailable = valueIsAvailableMethodGenerator.getMethod(valueHandler);
		final MethodSpec getValue = getValueMethodGenerator.generateFor(valueHandler);
		final MethodSpec getDefault = getDefaultMethodGenerator.generateFor(defaultAnno);
		final MethodSpec doInvocation = doInvocationGenerator.getMethod(e);

		final MethodSpec callMethod = CallerDef.CALL
				.toBuilder()
				.addCode(CodeBlock
						.builder()
						.addStatement(
								"final Object value = $N(attrs) ? $N(attrs) : $N(context, attrs)",
								valueIsAvailable,
								getValue,
								getDefault)
						.addStatement("$N(target, value)", doInvocation)
						.build())
				.build();

		return getEmptyAnonymousCaller(getNameOfTargetClass(e))
				.addMethod(callMethod)
				.addMethod(valueIsAvailable)
				.addMethod(getValue)
				.addMethod(getDefault)
				.addMethod(doInvocation)
				.build();
	}

	private TypeSpec.Builder getEmptyAnonymousCaller(final TypeName targetType) {
		final ClassName genericCaller = ClassName.get(CallerDef.SRC_FILE.packageName, CallerDef.ABSTRACT_CALLER.name);
		final TypeName specificCaller = ParameterizedTypeName.get(genericCaller, targetType);

		return TypeSpec
				.anonymousClassBuilder("")
				.addSuperinterface(specificCaller);
	}

	private TypeName getNameOfNonUseParameter(final ExecutableElement e) {
		for (final VariableElement parameter : e.getParameters()) {
			if (!UseAnnoRetriever.hasAnnotation(parameter)) {
				final TypeName className = ClassName.get(parameter.asType());

				if (className.isBoxedPrimitive()) {
					return className.unbox();
				} else {
					return className;
				}
			}
		}

		throw new RuntimeException("No non-use argument found.");
	}

	private TypeName getNameOfTargetClass(final ExecutableElement method) {
		final TypeElement enclosingType = (TypeElement) method.getEnclosingElement();
		return TypeName.get(enclosingType.asType());
	}
}