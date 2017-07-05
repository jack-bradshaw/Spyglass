package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
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
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processor.annotation_utils.CallHandlerAnnoUtil.getCallHandlerAnnotationMirror;
import static com.matthewtamlin.spyglass.processor.annotation_utils.CallHandlerAnnoUtil.hasCallHandlerAnnotation;
import static com.matthewtamlin.spyglass.processor.annotation_utils.DefaultAnnoUtil.getDefaultAnnotationMirror;
import static com.matthewtamlin.spyglass.processor.annotation_utils.DefaultAnnoUtil.hasDefaultAnnotation;
import static com.matthewtamlin.spyglass.processor.annotation_utils.UseAnnotationUtil.hasUseAnnotation;
import static com.matthewtamlin.spyglass.processor.annotation_utils.ValueHandlerAnnotationUtil.getValueHandlerAnnotationMirror;
import static com.matthewtamlin.spyglass.processor.annotation_utils.ValueHandlerAnnotationUtil.hasValueHandlerAnnotation;
import static javax.lang.model.element.Modifier.PUBLIC;

@Tested(testMethod = "automated")
public class CallerGenerator {
	private final GetDefaultMethodGenerator getDefaultMethodGenerator;

	private final GetValueMethodGenerator getValueMethodGenerator;

	private final ValueIsAvailableMethodGenerator valueIsAvailableMethodGenerator;

	private final SpecificValueIsAvailableMethodGenerator specificValueIsAvailableMethodGenerator;

	private final DoInvocationGenerator doInvocationGenerator;

	public CallerGenerator(final Elements elementUtil, final Types typeUtil) {
		checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
		checkNotNull(typeUtil, "Argument \'typeUtil\' cannot be null.");

		getDefaultMethodGenerator = new GetDefaultMethodGenerator(elementUtil);
		getValueMethodGenerator = new GetValueMethodGenerator(elementUtil);
		valueIsAvailableMethodGenerator = new ValueIsAvailableMethodGenerator(elementUtil);
		specificValueIsAvailableMethodGenerator = new SpecificValueIsAvailableMethodGenerator(elementUtil);
		doInvocationGenerator = new DoInvocationGenerator(elementUtil, typeUtil);
	}

	public TypeSpec generateCaller(final ExecutableElement method) {
		checkNotNull(method, "Argument \'method\' cannot be null.");

		if (hasCallHandlerAnnotation(method)) {
			return generateCallHandlerCaller(method);

		} else if (hasValueHandlerAnnotation(method)) {
			return hasDefaultAnnotation(method) ?
					generateValueHandlerCallerWithDefault(method) :
					generateValueHandlerCallerWithoutDefault(method);

		} else {
			throw new IllegalArgumentException("Argument \'method\' has neither a value handler annotation nor a call" +
					" handler annotation.");
		}
	}

	private TypeSpec generateCallHandlerCaller(final ExecutableElement e) {
		final AnnotationMirror callHandlerAnno = getCallHandlerAnnotationMirror(e);

		final MethodSpec specificValueIsAvailable = specificValueIsAvailableMethodGenerator.getMethod(callHandlerAnno);
		final MethodSpec doInvocation = doInvocationGenerator.getMethod(e);

		final MethodSpec call = getEmptyCallMethod(getNameOfTargetClass(e))
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
		final AnnotationMirror valueHandlerAnno = getValueHandlerAnnotationMirror(e);

		final MethodSpec valueIsAvailable = valueIsAvailableMethodGenerator.getMethod(valueHandlerAnno);
		final MethodSpec getValue = getValueMethodGenerator.getMethod(valueHandlerAnno);
		final MethodSpec doInvocation = doInvocationGenerator.getMethod(e);

		final MethodSpec call = getEmptyCallMethod(getNameOfTargetClass(e))
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
		final AnnotationMirror valueHandler = getValueHandlerAnnotationMirror(e);
		final AnnotationMirror defaultAnno = getDefaultAnnotationMirror(e);

		final MethodSpec valueIsAvailable = valueIsAvailableMethodGenerator.getMethod(valueHandler);
		final MethodSpec getValue = getValueMethodGenerator.getMethod(valueHandler);
		final MethodSpec getDefault = getDefaultMethodGenerator.getMethod(defaultAnno);
		final MethodSpec doInvocation = doInvocationGenerator.getMethod(e);

		final MethodSpec callMethod = getEmptyCallMethod(getNameOfTargetClass(e))
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
		final ClassName genericCaller = ClassName.get(CallerDef.PACKAGE, CallerDef.INTERFACE_NAME);
		final TypeName specificCaller = ParameterizedTypeName.get(genericCaller, targetType);

		return TypeSpec
				.anonymousClassBuilder("")
				.addSuperinterface(specificCaller);
	}

	private MethodSpec.Builder getEmptyCallMethod(final TypeName targetType) {
		return MethodSpec
				.methodBuilder(CallerDef.METHOD_NAME)
				.returns(void.class)
				.addModifiers(PUBLIC)
				.addParameter(targetType, "target")
				.addParameter(AndroidClassNames.CONTEXT, "context")
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs");
	}

	private TypeName getNameOfNonUseParameter(final ExecutableElement e) {
		for (final VariableElement parameter : e.getParameters()) {
			if (!hasUseAnnotation(parameter)) {
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