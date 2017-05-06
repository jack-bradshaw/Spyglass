package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processors.util.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;

public class InvocationLiteralBuilder {
	private static final Map<Class<? extends Annotation>, ParametrisedSupplier<Annotation, String>>
			CODE_LITERAL_SUPPLIERS;

	static {
		CODE_LITERAL_SUPPLIERS = new HashMap<>();

		CODE_LITERAL_SUPPLIERS.put(
				UseBoolean.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseBoolean castAnno = (UseBoolean) object;
						return castAnno.value() ? "true" : "false";
					}
				});
	}

	public static String buildFor(final ExecutableElement e) {
		return assembleLiteral(e, getLiteralsFromUseAnnotations(e));
	}

	public static String buildFor(
			final ExecutableElement e,
			final String nonUseArgLiteral) {

		final Map<Integer, String> argumentLiterals = getLiteralsFromUseAnnotations(e);

		for (int i = 0; i < argumentLiterals.size(); i++) {
			if (!argumentLiterals.containsKey(i)) {
				argumentLiterals.put(i, nonUseArgLiteral);
				break;
			}
		}

		return assembleLiteral(e, argumentLiterals);
	}

	private static String assembleLiteral(final ExecutableElement e, final Map<Integer, String> argumentLiterals) {
		final StringBuilder fullInvocationLiteralBuilder = new StringBuilder();

		// "someMethod"
		final String methodName = e.getSimpleName().toString();
		fullInvocationLiteralBuilder.append(methodName);

		// "someMethod("
		fullInvocationLiteralBuilder.append("(");

		// "someMethod(1, "something", "true", 3L, 1.4F
		for (int i = 0; i < argumentLiterals.size(); i++) {
			fullInvocationLiteralBuilder.append(argumentLiterals.get(i));

			if (i < argumentLiterals.size() - 1) {
				fullInvocationLiteralBuilder.append(", ");
			}
		}

		// "someMethod(1, "something", "true", 3L, 1.4F)
		fullInvocationLiteralBuilder.append(")");

		return fullInvocationLiteralBuilder.toString();
	}

	private static Map<Integer, String> getLiteralsFromUseAnnotations(final ExecutableElement e) {
		final Map<Integer, Annotation> useAnnotations = AnnotationUtil.getUseAnnotations(e);
		final Map<Integer, String> argumentLiterals = new HashMap<>();

		for (final Integer i : useAnnotations.keySet()) {
			final Annotation a = useAnnotations.get(i);
			final String codeLiteral = CODE_LITERAL_SUPPLIERS.get(a.annotationType()).supplyFor(a);

			argumentLiterals.put(i, codeLiteral);
		}

		return argumentLiterals;
	}
}