package com.matthewtamlin.spyglass.processor.core;

import com.matthewtamlin.spyglass.processor.code_generation.AndroidClassNames;
import com.matthewtamlin.spyglass.processor.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processor.code_generation.CallerGenerator;
import com.matthewtamlin.spyglass.processor.grouper.Grouper;
import com.matthewtamlin.spyglass.processor.grouper.TypeElementWrapper;
import com.matthewtamlin.spyglass.processor.validation.BasicValidator;
import com.matthewtamlin.spyglass.processor.validation.Result;
import com.matthewtamlin.spyglass.processor.validation.TypeValidator;
import com.matthewtamlin.spyglass.processor.validation.Validator;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
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
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import static javax.tools.Diagnostic.Kind.ERROR;

public class MainProcessor extends AbstractProcessor {
	private static final Set<Class<? extends Annotation>> SUPPORTED_ANNOTATIONS;

	private Elements elementUtil;

	private Messager messager;

	private Filer filer;

	private CallerGenerator callerGenerator;

	private Validator basicValidator;

	private TypeValidator typeValidator;

	private boolean callerFileCreatedSuccessfully;

	private boolean callerFileFailureMessageDisplayed;

	static {
		final Set<Class<? extends Annotation>> intermediateSet = new HashSet<>();

		intermediateSet.addAll(AnnotationRegistry.CALL_HANDLER_ANNOS);
		intermediateSet.addAll(AnnotationRegistry.VALUE_HANDLER_ANNOS);
		intermediateSet.addAll(AnnotationRegistry.DEFAULT_ANNOS);

		SUPPORTED_ANNOTATIONS = Collections.unmodifiableSet(intermediateSet);
	}

	@Override
	public synchronized void init(final ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);

		elementUtil = processingEnvironment.getElementUtils();
		messager = processingEnvironment.getMessager();
		filer = processingEnvironment.getFiler();

		final CoreHelpers coreHelpers = new CoreHelpers(
				processingEnvironment.getElementUtils(),
				processingEnvironment.getTypeUtils());

		callerGenerator = new CallerGenerator(coreHelpers);
		basicValidator = new BasicValidator();
		typeValidator = new TypeValidator(coreHelpers);

		callerFileCreatedSuccessfully = createFile(CallerDef.SRC_FILE, "Could not create Caller class file.");
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
		if (!callerFileCreatedSuccessfully) {
			if (!callerFileFailureMessageDisplayed) {
				messager.printMessage(ERROR, "Base Caller class could not be created. Aborting Spyglass processing.");
				callerFileFailureMessageDisplayed = true;
			}

			return false;
		}

		try {
			final Set<ExecutableElement> allElements = findSupportedElements(roundEnv);

			if (allElementsPassValidation(allElements, basicValidator)) {
				if (allElementsPassValidation(allElements, typeValidator)) {
					createCompanions(allElements);
				}
			}
		} catch (final Throwable t) {
			messager.printMessage(
					ERROR,
					String.format(
							"An unknown error occurred while processing Spyglass annotations. Please update to the " +
									"latest version of Spyglass, or report the issue if a newer version does not " +
									"exist. Exception message: \'%1$s\'. Stacktrace: \'%2$s\'.",
							t.getMessage(),
							Arrays.toString(t.getStackTrace())));
		}

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

	private boolean allElementsPassValidation(final Set<? extends Element> elements, final Validator validator) {
		boolean allPassed = true;

		for (final Element element : elements) {
			// This check should never fail since handler and default annotations are restricted to methods
			if (element.getKind() != ElementKind.METHOD) {
				throw new RuntimeException("A handler or default annotation was found on a non-method element.");
			}

			final Result result = validator.validate((ExecutableElement) element);

			if (!result.isSuccessful()) {
				allPassed = false;
				messager.printMessage(ERROR, result.getDescription(), element);
			}
		}

		return allPassed;
	}

	private void createCompanions(final Set<ExecutableElement> elements) {
		final Map<TypeElementWrapper, Set<ExecutableElement>> sortedElements = Grouper.groupByEnclosingClass(elements);

		for (final TypeElementWrapper targetClass : sortedElements.keySet()) {
			final MethodSpec.Builder activateCallers = MethodSpec
					.methodBuilder("activateCallers")
					.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
					.returns(void.class)
					.addParameter(TypeName.get(targetClass.unwrap().asType()), "target")
					.addParameter(AndroidClassNames.CONTEXT, "context")
					.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs");

			final CodeBlock.Builder methodBody = CodeBlock.builder();

			boolean firstLoop = true;

			for (final ExecutableElement method : sortedElements.get(targetClass)) {
				final TypeSpec anonymousCaller = callerGenerator.generateFor(
						method,
						CodeBlock.of("target"),
						CodeBlock.of("context"),
						CodeBlock.of("attrs"));

				if (firstLoop) {
					firstLoop = false;
				} else {
					methodBody.add("\n");
				}

				methodBody.addStatement("$L.call()", anonymousCaller);
			}

			activateCallers.addCode(methodBody.build());

			final TypeSpec companionClass = TypeSpec
					.classBuilder(CompanionNamer.getCompanionNameFor(targetClass.unwrap()))
					.addMethod(activateCallers.build())
					.build();

			final PackageElement targetClassPackage = elementUtil.getPackageOf(targetClass.unwrap());

			final JavaFile companionFile = JavaFile
					.builder(targetClassPackage.getQualifiedName().toString(), companionClass)
					.addFileComment("Generated by the Spyglass framework. Do not modify!")
					.indent("\t")
					.skipJavaLangImports(true)
					.build();

			createFile(
					companionFile,
					"Could not create companion file for class " + targetClass.unwrap().getSimpleName() + ".");
		}
	}

	private boolean createFile(final JavaFile file, final String errorMessage) {
		try {
			file.writeTo(filer);

			return true;

		} catch (final IOException e) {
			messager.printMessage(ERROR, errorMessage);

			return false;
		}
	}
}