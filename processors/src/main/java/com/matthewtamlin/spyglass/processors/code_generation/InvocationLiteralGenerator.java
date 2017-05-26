package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
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
import com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil;
import com.matthewtamlin.spyglass.processors.annotation_utils.UseAnnotationUtil;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.squareup.javapoet.CodeBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class InvocationLiteralGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, String>> argLiteralSuppliers = new HashMap<>();

	private Elements elementsUtil;

	{
		argLiteralSuppliers.put(
				UseBoolean.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return value.toString();
					}
				});

		argLiteralSuppliers.put(
				UseByte.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return "(byte)" + value.toString();
					}
				}
		);

		argLiteralSuppliers.put(
				UseChar.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return "(char)" + value.toString();
					}
				}
		);

		argLiteralSuppliers.put(
				UseDouble.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return value.toString();
					}
				}
		);

		argLiteralSuppliers.put(
				UseFloat.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return value.toString();
					}
				}
		);

		argLiteralSuppliers.put(
				UseInt.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return value.toString();
					}
				}
		);

		argLiteralSuppliers.put(
				UseLong.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return value.toString();
					}
				}
		);

		argLiteralSuppliers.put(
				UseNull.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						return "null";
					}
				}
		);

		argLiteralSuppliers.put(
				UseShort.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						return "(short)" + value.toString();
					}
				}
		);

		argLiteralSuppliers.put(
				UseString.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue value = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementsUtil);

						// Use the String escaping tools of the JavaPoet library
						return CodeBlock
								.builder()
								.add("$S", value.toString())
								.build()
								.toString();
					}
				}
		);
	}

	public InvocationLiteralGenerator(final Elements elementsUtil) {
		this.elementsUtil = checkNotNull(elementsUtil, "Argument \'elementsUtil\' cannot be null.");
	}

	public String buildInvocationLiteralFor(final ExecutableElement e) {
		checkNotNull(e, "Argument \'e\' cannot be null.");

		final String methodName = e.getSimpleName().toString();
		final List<String> argLiterals = getArgLiteralsFromUseAnnotations(e);

		return methodName + "(" + listToCommaSeparatedString(argLiterals) + ")";
	}

	public String buildInvocationLiteralFor(final ExecutableElement e, final String extraArgLiteral) {
		checkNotNull(e, "Argument \'e\' cannot be null.");
		checkNotNull(extraArgLiteral, "Argument \'extraArgLiteral\' cannot be null.");

		final String methodName = e.getSimpleName().toString();
		final List<String> argLiterals = getArgLiteralsFromUseAnnotations(e);

		argLiterals.set(argLiterals.indexOf(null), extraArgLiteral);

		return e.getSimpleName() + "(" + listToCommaSeparatedString(argLiterals) + ")";
	}

	private List<String> getArgLiteralsFromUseAnnotations(final ExecutableElement e) {
		final List<String> argLiterals = new ArrayList<>();

		for (final VariableElement parameter : e.getParameters()) {
			final AnnotationMirror useAnnotationMirror = UseAnnotationUtil.getUseAnnotationMirror(parameter);

			if (useAnnotationMirror == null) {
				argLiterals.add(null);

			} else {
				final String annotationTypeName = useAnnotationMirror.getAnnotationType().toString();
				argLiterals.add(argLiteralSuppliers.get(annotationTypeName).supplyFor(useAnnotationMirror));
			}
		}

		return argLiterals;
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