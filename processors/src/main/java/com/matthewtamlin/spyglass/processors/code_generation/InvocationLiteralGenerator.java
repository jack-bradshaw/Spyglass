package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil;
import com.matthewtamlin.spyglass.processors.annotation_utils.UseAnnotationUtil;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class InvocationLiteralGenerator {
	private final Elements elementsUtil;

	public InvocationLiteralGenerator(final Elements elementsUtil) {
		this.elementsUtil = checkNotNull(elementsUtil, "Argument \'elementsUtil\' cannot be null.");
	}

	public String buildInvocationLiteralFor(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		final String methodName = element.getSimpleName().toString();
		final List<String> argLiterals = getArgLiteralsFromUseAnnotations(element);

		return methodName + "(" + listToCommaSeparatedString(argLiterals) + ")";
	}

	public String buildInvocationLiteralFor(final ExecutableElement element, final String extraArgLiteral) {
		checkNotNull(element, "Argument \'element\' cannot be null.");
		checkNotNull(extraArgLiteral, "Argument \'extraArgLiteral\' cannot be null.");

		final String methodName = element.getSimpleName().toString();
		final List<String> argLiterals = getArgLiteralsFromUseAnnotations(element);

		argLiterals.set(argLiterals.indexOf(null), extraArgLiteral);

		return element.getSimpleName() + "(" + listToCommaSeparatedString(argLiterals) + ")";
	}

	private List<String> getArgLiteralsFromUseAnnotations(final ExecutableElement e) {
		final List<String> argLiterals = new ArrayList<>();

		for (final VariableElement parameter : e.getParameters()) {
			final AnnotationMirror useAnnotationMirror = UseAnnotationUtil.getUseAnnotationMirror(parameter);

			if (useAnnotationMirror == null) {
				argLiterals.add(null);

			} else {
				argLiterals.add(getLiteralFromAnnotationMirror(useAnnotationMirror));
			}
		}

		return argLiterals;
	}

	private String getLiteralFromAnnotationMirror(final AnnotationMirror mirror) {
		if (mirror.getAnnotationType().toString().equals(UseNull.class.getName())) {
			return "null";
		} else {
			// the toString method of AnnotationValue produces a source code representation
			return AnnotationMirrorUtil.getAnnotationValueWithDefaults(mirror, "value", elementsUtil).toString();
		}
	}

	private static String listToCommaSeparatedString(final List<String> list) {
		final StringBuilder result = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			result.append(list.get(i));

			if (i < list.size() - 1) {
				result.append(", ");
			}
		}

		return result.toString();
	}
}