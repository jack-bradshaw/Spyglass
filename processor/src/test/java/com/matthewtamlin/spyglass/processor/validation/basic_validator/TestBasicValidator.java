package com.matthewtamlin.spyglass.processor.validation.basic_validator;

import com.matthewtamlin.avatar.rules.AvatarRule;
import com.matthewtamlin.spyglass.processor.validation.BasicValidator;
import com.matthewtamlin.spyglass.processor.validation.Result;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(JUnit4.class)
public class TestBasicValidator {
	@Rule
	public final AvatarRule avatarRule = AvatarRule
			.builder()
			.withSourcesAt(
					"processor/src/test/java/com/matthewtamlin/spyglass/processor/validation/basic_validator/Data.java")
			.build();

	private Set<Element> elements;

	private BasicValidator validator;

	@Before
	public void setup() {
		elements = avatarRule.getElementsWithAnnotation(Target.class);

		validator = new BasicValidator();
	}

	@Test
	public void testValidateElement_usingDataFileElements() {
		for (final Element element : elements) {
			if (element.getKind() != ElementKind.METHOD) {
				throw new RuntimeException("All test elements must be executable elements (e.g. methods).");
			}

			final Target targetAnnotation = element.getAnnotation(Target.class);
			final boolean shouldPassValidation = targetAnnotation.isValid();
			final Result validationResult = validator.validate((ExecutableElement) element);

			assertThat(validationResult.isSuccessful(), is(shouldPassValidation));
		}
	}
}