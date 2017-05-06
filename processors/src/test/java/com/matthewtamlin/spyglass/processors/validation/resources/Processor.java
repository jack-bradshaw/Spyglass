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

import static com.google.common.truth.Truth.assertWithMessage;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Processor extends AbstractProcessor {
	private static final String GENERIC_ERROR_MESSAGE = "Test for %1$s failed with reason \"%2$s\"";

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

			final boolean shouldPass = targetAnnotation.isValid();
			final boolean doesPass = passesValidation(e);

			final String formattedErrorMessage = String.format(
					GENERIC_ERROR_MESSAGE,
					e.getSimpleName(),
					targetAnnotation.failureMessage());

			assertThat(formattedErrorMessage, doesPass, is(shouldPass));
		}

		return false;
	}

	public boolean passesValidation(final Element element) {
		try {
			Validator.validateElement(element);
			return true;
		} catch (final ValidationException exception) {
			return false;
		}
	}
}