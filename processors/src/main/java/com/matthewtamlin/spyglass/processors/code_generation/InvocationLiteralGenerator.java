package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.processors.annotation_utils.UseAnnotationUtil;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.squareup.javapoet.CodeBlock;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.ExecutableElement;

public class InvocationLiteralGenerator {
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

		CODE_LITERAL_SUPPLIERS.put(
				UseByte.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseByte castAnno = (UseByte) object;
						return Integer.toString(castAnno.value());
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseChar.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseChar castAnno = (UseChar) object;
						final String hexValue = Integer.toHexString(castAnno.value()).toUpperCase();
						return "\'\\u" + hexValue + "\'";
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseDouble.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseDouble castAnno = (UseDouble) object;
						return Double.toString(castAnno.value());
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseFloat.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseFloat castAnno = (UseFloat) object;
						return Float.toString(castAnno.value()) + "F";
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseInt.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseInt castAnno = (UseInt) object;
						return Integer.toString(castAnno.value());
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseLong.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseLong castAnno = (UseLong) object;
						return Long.toString(castAnno.value()) + "L";
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseNull.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						return "null";
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseShort.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseShort castAnno = (UseShort) object;
						return Short.toString(castAnno.value());
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseString.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseString castAnno = (UseString) object;

						// Use the String escaping tools of the JavaPoet library
						return CodeBlock
								.builder()
								.add("$S", castAnno.value())
								.build()
								.toString();
					}
				}
		);
	}

	public static String buildInvocationLiteralFor(final ExecutableElement e) {
		final String methodName = e.getSimpleName().toString();
		final Map<Integer, String> argLiterals = getArgLiteralsFromUseAnnotations(e);

		return methodName + combineArgLiterals(argLiterals);
	}

	public static String buildInvocationLiteralFor(
			final ExecutableElement e,
			final String nonUseArgLiteral) {

		final String methodName = e.getSimpleName().toString();
		final Map<Integer, String> argLiterals = getArgLiteralsFromUseAnnotations(e);

		// Find the first index which is not mapped to a literal, and set it to the nonUseArgLiteral value
		for (int i = 0; i < argLiterals.size() + 1; i++) {
			if (!argLiterals.containsKey(i)) {
				argLiterals.put(i, nonUseArgLiteral);
				break;
			}
		}

		return e.getSimpleName() + combineArgLiterals(argLiterals);
	}

	private static Map<Integer, String> getArgLiteralsFromUseAnnotations(final ExecutableElement e) {
		final Map<Integer, String> argumentLiterals = new HashMap<>();

		final Map<Integer, Annotation> useAnnotations = UseAnnotationUtil.getUseAnnotations(e);

		for (final Integer i : useAnnotations.keySet()) {
			final Annotation a = useAnnotations.get(i);
			final String codeLiteral = CODE_LITERAL_SUPPLIERS.get(a.annotationType()).supplyFor(a);

			argumentLiterals.put(i, codeLiteral);
		}

		return argumentLiterals;
	}

	private static String combineArgLiterals(final Map<Integer, String> argumentLiterals) {
		final StringBuilder fullInvocationLiteralBuilder = new StringBuilder();

		fullInvocationLiteralBuilder.append("(");

		for (int i = 0; i < argumentLiterals.size(); i++) {
			fullInvocationLiteralBuilder.append(argumentLiterals.get(i));

			if (i < argumentLiterals.size() - 1) {
				fullInvocationLiteralBuilder.append(", ");
			}
		}

		fullInvocationLiteralBuilder.append(")");

		return fullInvocationLiteralBuilder.toString();
	}
}