package com.matthewtamlin.spyglass.processors.core;

import com.matthewtamlin.spyglass.processors.code_generation.AndroidClassNames;
import com.matthewtamlin.spyglass.processors.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processors.code_generation.CallerGenerator;
import com.matthewtamlin.spyglass.processors.grouper.TypeElementWrapper;
import com.matthewtamlin.spyglass.processors.validation.ValidationException;
import com.matthewtamlin.spyglass.processors.validation.Validator;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.spyglass.processors.grouper.Grouper.groupByEnclosingClass;
import static javax.tools.Diagnostic.Kind.ERROR;

public class MainProcessor extends AbstractProcessor {
	private static final Set<Class<? extends Annotation>> SUPPORTED_ANNOTATIONS;

	private Messager messager;

	private Filer filer;

	private Elements elementUtil;

	private CallerGenerator callerGenerator;

	static {
		final Set<Class<? extends Annotation>> intermediateSet = new HashSet<>();

		intermediateSet.addAll(AnnotationRegistry.CALL_HANDLER_ANNOTATIONS);
		intermediateSet.addAll(AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS);
		intermediateSet.addAll(AnnotationRegistry.DEFAULT_ANNOTATIONS);

		SUPPORTED_ANNOTATIONS = Collections.unmodifiableSet(intermediateSet);
	}

	@Override
	public synchronized void init(final ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);

		callerGenerator = new CallerGenerator(processingEnvironment.getElementUtils());

		messager = processingEnvironment.getMessager();
		filer = processingEnvironment.getFiler();
		elementUtil = processingEnvironment.getElementUtils();

		createFile(CallerDef.getJavaFile(), "Could not create Caller class file.");
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		final Set<String> supportedTypes = new HashSet<>();

		for (Class<? extends Annotation> clazz : SUPPORTED_ANNOTATIONS) {
			supportedTypes.add(clazz.getCanonicalName());
		}

		return supportedTypes;
	}

	@Override
	public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
		final Set<ExecutableElement> allElements = findSupportedElements(roundEnv);
		validateElements(allElements);
		createCompanions(allElements);

		return false;
	}

	private Set<ExecutableElement> findSupportedElements(final RoundEnvironment roundEnv) {
		final Set<ExecutableElement> supportedElements = new HashSet<>();

		for (final Class<? extends Annotation> annoType : SUPPORTED_ANNOTATIONS) {
			for (final Element foundElement : roundEnv.getElementsAnnotatedWith(annoType)) {
				// All elements should be executable elements
				supportedElements.add((ExecutableElement) foundElement);
			}
		}

		return supportedElements;
	}

	private void validateElements(final Set<? extends Element> elements) {
		for (final Element element : elements) {
			try {
				Validator.validateElement(element);

			} catch (final ValidationException e1) {
				messager.printMessage(ERROR, e1.getMessage(), element);

			} catch (final Exception e2) {
				messager.printMessage(ERROR, "Spyglass validation failure.", element);
			}
		}
	}

	private void createCompanions(final Set<ExecutableElement> elements) {
		final Map<TypeElementWrapper, Set<ExecutableElement>> sortedElements = groupByEnclosingClass(elements);

		for (final TypeElementWrapper targetClass : sortedElements.keySet()) {
			final CodeBlock.Builder activateCallersMethodBodyBuilder = CodeBlock.builder();

			for (final ExecutableElement method : sortedElements.get(targetClass)) {
				final TypeSpec anonymousCaller = callerGenerator.generateCaller(method);

				//TODO need to make sure this actually generates an anonymous class as expected
				activateCallersMethodBodyBuilder.addStatement("new $L.callMethod(target, context, attrs)", anonymousCaller);
				activateCallersMethodBodyBuilder.add("\n");
			}

			final MethodSpec activateCallersMethod = MethodSpec
					.methodBuilder("activateCallers")
					.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
					.returns(void.class)
					.addParameter(TypeName.get(targetClass.unwrap().asType()), "target")
					.addParameter(AndroidClassNames.CONTEXT, "context")
					.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
					.addCode(activateCallersMethodBodyBuilder.build())
					.build();

			final TypeSpec companionClass = TypeSpec
					.classBuilder(targetClass.unwrap().getSimpleName() + "_SpyglassCompanion")
					.addMethod(activateCallersMethod)
					.build();

			final JavaFile companionFile = JavaFile
					.builder(
							elementUtil.getPackageOf(targetClass.unwrap()).getQualifiedName().toString(),
							companionClass)
					.addFileComment("Generated by the Spyglass framework. Do not modify!")
					.indent("\t")
					.build();

			createFile(
					companionFile,
					"Could not create companion file for class " + targetClass.unwrap().getSimpleName() + ".");
		}
	}

	private void createFile(final JavaFile file, final String errorMessage) {
		try {
			file.writeTo(filer);
		} catch (final IOException e) {
			messager.printMessage(ERROR, errorMessage);
		}
	}
}