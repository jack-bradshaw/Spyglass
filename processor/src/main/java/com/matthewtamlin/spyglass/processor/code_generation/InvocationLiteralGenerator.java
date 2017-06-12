package com.matthewtamlin.spyglass.processor.code_generation;

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
import com.matthewtamlin.spyglass.processor.annotation_utils.AnnotationMirrorUtil;
import com.matthewtamlin.spyglass.processor.annotation_utils.UseAnnotationUtil;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;

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
	private final Map<String, ParametrisedSupplier<AnnotationMirror, String>> methodBodySuppliers;

	private final Elements elementUtil;

	{
		methodBodySuppliers = new HashMap<>();

		methodBodySuppliers.put(
				UseBoolean.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseByte.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseChar.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseDouble.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseFloat.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseInt.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseLong.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseNull.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						return "null";
					}
				});

		methodBodySuppliers.put(
				UseShort.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return "(short)" + rawValue.toString();
					}
				});

		methodBodySuppliers.put(
				UseString.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, String>() {
					@Override
					public String supplyFor(final AnnotationMirror object) {
						final AnnotationValue rawValue = AnnotationMirrorUtil.getAnnotationValueWithDefaults(
								object,
								"value",
								elementUtil);

						return rawValue.toString();
					}
				});
	}

	public InvocationLiteralGenerator(final Elements elementUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
	}

	public String generateLiteralWithoutExtraArg(final ExecutableElement element) {
		checkNotNull(element, "Argument \'element\' cannot be null.");

		final List<String> argLiterals = useAnnotationsToLiterals(element);

		return element.getSimpleName() + "(" + listToCommaSeparatedString(argLiterals) + ")";
	}

	public String generateLiteralWithExtraArg(final ExecutableElement element, final String extraArgLiteral) {
		checkNotNull(element, "Argument \'element\' cannot be null.");
		checkNotNull(extraArgLiteral, "Argument \'extraArgLiteral\' cannot be null.");

		final List<String> argLiterals = useAnnotationsToLiterals(element);

		// One param should be missing a use annotation, so find the null literal and replace with the extra literal
		argLiterals.set(argLiterals.indexOf(null), extraArgLiteral);

		return element.getSimpleName() + "(" + listToCommaSeparatedString(argLiterals) + ")";
	}

	private List<String> useAnnotationsToLiterals(final ExecutableElement e) {
		final List<String> argLiterals = new ArrayList<>();

		for (final VariableElement parameter : e.getParameters()) {
			final AnnotationMirror useAnnotationMirror = UseAnnotationUtil.getUseAnnotationMirror(parameter);

			if (useAnnotationMirror == null) {
				argLiterals.add(null);
			} else {
				final String useAnnotationType = useAnnotationMirror.getAnnotationType().toString();
				argLiterals.add(methodBodySuppliers.get(useAnnotationType).supplyFor(useAnnotationMirror));
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