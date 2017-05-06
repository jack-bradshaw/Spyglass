package com.matthewtamlin.spyglass.processors.validation.resources;

import com.matthewtamlin.spyglass.processors.validation.ValidationException;
import com.matthewtamlin.spyglass.processors.validation.Validator;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static javax.tools.Diagnostic.Kind.ERROR;

public class Processor extends AbstractProcessor {
	private Messager messager;

	@Override
	public synchronized void init(final ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);

		this.messager = processingEnvironment.getMessager();
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		final Set<String> supportedAnnotationTypes = new HashSet<>();

		supportedAnnotationTypes.add(Target.class.getCanonicalName());

		return supportedAnnotationTypes;
	}

	@Override
	public boolean process(final Set<? extends TypeElement> set, final RoundEnvironment roundEnvironment) {
		for (final Element e : roundEnvironment.getElementsAnnotatedWith(Target.class)) {
			final Target targetAnnotation = e.getAnnotation(Target.class);
			final boolean shouldPassValidation = targetAnnotation.isValid();

			try {
				Validator.validateElement(e);

				if (!shouldPassValidation) {
					messager.printMessage(
							ERROR,
							String.format("Element %1$s should have failed validation but passed.", e.getSimpleName()));
				}
			} catch (final ValidationException exception) {
				if (shouldPassValidation) {
					messager.printMessage(
							ERROR,
							String.format("Element %1$s should have passed validation but failed. Error message: %2$s",
									e.getSimpleName(),
									exception.getMessage()));
				}
			}
		}

		return false;
	}
}