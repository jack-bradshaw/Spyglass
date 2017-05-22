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
import com.matthewtamlin.spyglass.annotations.use_annotations.UseSuppliedValue;
import com.matthewtamlin.spyglass.processors.annotation_utils.UseAnnotationUtil;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processors.supplier_instantiator.SupplierInstantiator;
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
						return CodeBlock.builder().add("$S", castAnno.value()).toString();
					}
				}
		);

		CODE_LITERAL_SUPPLIERS.put(
				UseSuppliedValue.class,
				new ParametrisedSupplier<Annotation, String>() {
					@Override
					public String supplyFor(final Annotation object) {
						final UseSuppliedValue castAnno = (UseSuppliedValue) object;
						return CodeBlock
								.builder()
								.add("$T.instantiateSupplier($L)", SupplierInstantiator.class, castAnno.value())
								.toString();
					}
				}
		);
	}

	public static String buildInvocationLiteralFor(final ExecutableElement e) {
		// A method name such as 'doSomeTask'
		final String methodName = e.getSimpleName().toString();

		// A map of argument literals by index, such as '[0: "hello", 1: 10L, 2: false, 3: 0b11]'
		final Map<Integer, String> argLiterals = getArgLiteralsFromUseAnnotations(e);

		// The actual method call literal such as 'doSomeTask("hello", 10L, false, 0b11)'
		return methodName + combineArgLiterals(argLiterals);
	}

	public static String buildInvocationLiteralFor(
			final ExecutableElement e,
			final String nonUseArgLiteral) {

		// A method name such as 'doSomeTask'
		final String methodName = e.getSimpleName().toString();

		// A map of argument literals by index, such as '[0: "hello", 1: 10L, 2: false, 3: 0b11]'
		final Map<Integer, String> argLiterals = getArgLiteralsFromUseAnnotations(e);

		// Find the first index which is not mapped to a literal and map it to the nonUseArgLiteral
		for (int i = 0; i < argLiterals.size(); i++) {
			if (!argLiterals.containsKey(i)) {
				argLiterals.put(i, nonUseArgLiteral);
				break;
			}
		}

		// The actual method call literal such as 'doSomeTask("hello", 10L, false, 0b11, "some non use arg")'
		return e.getSimpleName() + combineArgLiterals(argLiterals);
	}

	private static Map<Integer, String> getArgLiteralsFromUseAnnotations(final ExecutableElement e) {
		final Map<Integer, Annotation> useAnnotations = UseAnnotationUtil.getUseAnnotations(e);
		final Map<Integer, String> argumentLiterals = new HashMap<>();

		for (final Integer i : useAnnotations.keySet()) {
			final Annotation a = useAnnotations.get(i);
			final String codeLiteral = CODE_LITERAL_SUPPLIERS.get(a.annotationType()).supplyFor(a);

			argumentLiterals.put(i, codeLiteral);
		}

		return argumentLiterals;
	}

	private static String combineArgLiterals(final Map<Integer, String> argumentLiterals) {
		final StringBuilder fullInvocationLiteralBuilder = new StringBuilder();

		// Start with the opening bracket: '('
		fullInvocationLiteralBuilder.append("(");

		// Add the argument literals one by one using comma separation: '(1, "something", "true", 3L, 1.4F'
		for (int i = 0; i < argumentLiterals.size(); i++) {
			fullInvocationLiteralBuilder.append(argumentLiterals.get(i));

			if (i < argumentLiterals.size() - 1) {
				fullInvocationLiteralBuilder.append(", ");
			}
		}

		// Add the closing bracket: '(1, "something", "true", 3L, 1.4F)'
		fullInvocationLiteralBuilder.append(")");

		return fullInvocationLiteralBuilder.toString();
	}
}