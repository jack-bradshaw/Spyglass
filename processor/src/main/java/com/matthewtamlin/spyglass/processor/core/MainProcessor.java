package com.matthewtamlin.spyglass.processor.core;

import com.matthewtamlin.spyglass.processor.code_generation.AndroidClassNames;
import com.matthewtamlin.spyglass.processor.code_generation.CallerDef;
import com.matthewtamlin.spyglass.processor.code_generation.CallerGenerator;
import com.matthewtamlin.spyglass.processor.grouper.Grouper;
import com.matthewtamlin.spyglass.processor.grouper.TypeElementWrapper;
import com.matthewtamlin.spyglass.processor.validation.ValidationException;
import com.matthewtamlin.spyglass.processor.validation.Validator;
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
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static javax.tools.Diagnostic.Kind.ERROR;

public class MainProcessor extends AbstractProcessor {
	private static final String COMPANION_CLASS_NAME_SUFFIX = "_SpyglassCompanion";

	private static final Set<Class<? extends Annotation>> SUPPORTED_ANNOTATIONS;

	private Messager messager;

	private Filer filer;

	private CoreHelpers coreHelpers;

	private CallerGenerator callerGenerator;

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

		messager = processingEnvironment.getMessager();
		filer = processingEnvironment.getFiler();

		coreHelpers = new CoreHelpers(processingEnvironment.getElementUtils(), processingEnvironment.getTypeUtils());
		callerGenerator = new CallerGenerator(coreHelpers);

		createFile(CallerDef.SRC_FILE, "Could not create Caller class file.");
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
			// This check should never fail since handler and default annotations are restricted to methods
			if (element.getKind() != ElementKind.METHOD) {
				throw new RuntimeException("A handler or default annotation was found on a non-method element.");
			}

			try {
				final Validator validator = new Validator(coreHelpers);

				validator.validateElement((ExecutableElement) element);

			} catch (final ValidationException validationException) {
				messager.printMessage(ERROR, validationException.getMessage(), element);

			} catch (final Exception exception) {
				messager.printMessage(ERROR, "Spyglass validation failure.", element);
			}
		}
	}

	private void createCompanions(final Set<ExecutableElement> elements) {
		final Map<TypeElementWrapper, Set<ExecutableElement>> sortedElements = Grouper.groupByEnclosingClass(elements);

		for (final TypeElementWrapper targetClass : sortedElements.keySet()) {
			final CodeBlock.Builder methodBody = CodeBlock.builder();

			boolean firstLoop = true;

			for (final ExecutableElement method : sortedElements.get(targetClass)) {
				final TypeSpec anonymousCaller = callerGenerator.generateCaller(method);

				if (firstLoop) {
					firstLoop = false;
				} else {
					methodBody.add("\n");
				}

				methodBody.addStatement("$L.call(target, context, attrs)", anonymousCaller);
			}

			final MethodSpec activateCallers = MethodSpec
					.methodBuilder("activateCallers")
					.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
					.returns(void.class)
					.addParameter(TypeName.get(targetClass.unwrap().asType()), "target")
					.addParameter(AndroidClassNames.CONTEXT, "context")
					.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
					.addCode(methodBody.build())
					.build();

			final TypeSpec companionClass = TypeSpec
					.classBuilder(targetClass.unwrap().getSimpleName() + COMPANION_CLASS_NAME_SUFFIX)
					.addMethod(activateCallers)
					.build();

			final PackageElement targetClassPackage = coreHelpers.getElementHelper().getPackageOf(targetClass.unwrap());

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

	private void createFile(final JavaFile file, final String errorMessage) {
		try {
			file.writeTo(filer);
		} catch (final IOException e) {
			messager.printMessage(ERROR, errorMessage);
		}
	}
}