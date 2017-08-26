package com.matthewtamlin.spyglass.processor.validation;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

@RunWith(JUnit4.class)
public class TestValidator {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt("processor/src/test/java/com/matthewtamlin/spyglass/processor/validation/Data.java")
			.build();

	private Set<Element> elements;

	private Validator validator;

	@Before
	public void setup() {
		elements = avatarRule.getElementsWithAnnotation(Target.class);

		validator = new Validator(new CoreHelpers(
				avatarRule.getProcessingEnvironment().getElementUtils(),
				avatarRule.getProcessingEnvironment().getTypeUtils()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor_nullTypeMirrorHelper() {
		new Validator(null);
	}

	@Test
	public void testValidateElement_usingDataFileElements() {
		for (final Element element : elements) {
			final Target targetAnnotation = element.getAnnotation(Target.class);
			final boolean shouldPassValidation = targetAnnotation.isValid();

			if (element.getKind() != ElementKind.METHOD) {
				throw new RuntimeException("All test elements must be executable elements (e.g. methods).");
			}

			try {
				validator.validateElement((ExecutableElement) element);

				if (!shouldPassValidation) {
					throw new RuntimeException(
							String.format(
									"Element %1$s should have failed validation but passed.",
									element.getSimpleName()));
				}
			} catch (final ValidationException exception) {
				if (shouldPassValidation) {
					throw new RuntimeException(
							String.format(
									"Element %1$s should have passed validation but failed with error message: %2$s",
									element.getSimpleName(),
									exception.getMessage())
					);
				}
			}
		}
	}
}