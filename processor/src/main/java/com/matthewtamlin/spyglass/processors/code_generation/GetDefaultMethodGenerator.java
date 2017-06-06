package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.annotations.units.DimensionUnit;
import com.matthewtamlin.spyglass.processors.core.AnnotationRegistry;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processors.util.EnumUtil;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil.getAnnotationValueWithDefaults;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

@Tested(testMethod = "automated")
public class GetDefaultMethodGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>> methodBodySuppliers;

	private final Elements elementUtil;

	{
		methodBodySuppliers = new HashMap<>();

		methodBodySuppliers.put(
				DefaultToBoolean.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToBooleanResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return context.getResources().getBoolean($L)",
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToColorResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getColor(context, $L)",
										AndroidClassNames.CONTEXT_COMPAT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToColorStateListResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getColorStateList(context, $L)",
										AndroidClassNames.CONTEXT_COMPAT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToDimension.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						try {
							final String rawDimensionValue = getLiteralFromAnnotation(anno, "value");

							final String unitLiteral = getLiteralFromAnnotation(anno, "unit");
							final DimensionUnit unit = (DimensionUnit) EnumUtil.getEnumConstant(unitLiteral);

							return CodeBlock
									.builder()
									.addStatement(
											"$T metrics = context.getResources().getDisplayMetrics()",
											AndroidClassNames.DISPLAY_METRICS)
									.addStatement(
											"return $1T.applyDimension($1T.$2L, $3L, metrics)",
											AndroidClassNames.TYPED_VALUE,
											getComplexUnitLiteral(unit),
											rawDimensionValue)
									.build();

						} catch (final ClassNotFoundException e) {
							throw new RuntimeException("DimensionUnit class not found. This should never happen.");
						}
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToDimensionResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"context.getResources().getDimension($L)",
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToDrawableResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getDrawable(context, $L)",
										AndroidClassNames.CONTEXT_COMPAT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToEnumConstant.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return $T.getEnumConstant($L, $L)",
										TypeName.get(EnumUtil.class),
										getLiteralFromAnnotation(anno, "enumClass"),
										getLiteralFromAnnotation(anno, "ordinal"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToFloat.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToFractionResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return context.getResources().getFraction($L, $L, $L)",
										getLiteralFromAnnotation(anno, "resId"),
										getLiteralFromAnnotation(anno, "baseMultiplier"),
										getLiteralFromAnnotation(anno, "parentMultiplier"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToInteger.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToIntegerResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return context.getResources().getInteger($L)",
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToNull.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("return null")
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToString.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToStringResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return context.getResources().getString($L)",
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToTextArrayResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"context.getResources().getTextArray($L)",
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DefaultToTextResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"context.getResources().getText($L)",
										getLiteralFromAnnotation(anno, "resId"))
								.build();
					}
				}
		);
	}

	public GetDefaultMethodGenerator(final Elements elementUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
	}

	/**
	 * Creates a method spec equivalent to the following:
	 * <pre>{@code
	 * public Object getDefault(final TypedArray attrs) {
	 * 	dynamic implementation here
	 * }}</pre>
	 * <p>
	 * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
	 * returns some value using a context and a typed array. Exactly what is returned is determined by each specific
	 * implementation.
	 *
	 * @param anno
	 * 		the annotation to use when generating the method body, not null
	 *
	 * @return the method spec, not null
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code anno} is null
	 */
	public MethodSpec getMethod(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");
		checkIsDefaultAnnotation(anno, "Argument \'anno\' must be a mirror of a default annotation.");

		final String annotationType = anno.getAnnotationType().toString();

		return MethodSpec
				.methodBuilder("getDefault")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassNames.CONTEXT, "context", FINAL)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
				.addCode(methodBodySuppliers.get(annotationType).supplyFor(anno))
				.build();
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return getAnnotationValueWithDefaults(mirror, key, elementUtil).toString();
	}

	private String getComplexUnitLiteral(final DimensionUnit unit) {
		switch (unit) {
			case PX: {return "COMPLEX_UNIT_PX";}
			case DP: {return "COMPLEX_UNIT_DIP";}
			case PT: {return "COMPLEX_UNIT_PT";}
			case IN: {return "COMPLEX_UNIT_IN";}
			case SP: {return "COMPLEX_UNIT_SP";}
			case MM: {return "COMPLEX_UNIT_MM";}
		}

		throw new RuntimeException("Should never get here.");
	}

	private void checkIsDefaultAnnotation(final AnnotationMirror anno, final String exceptionMessage) {
		try {
			final Class annotationClass = (Class) Class.forName(anno.getAnnotationType().toString());

			if (!AnnotationRegistry.DEFAULT_ANNOTATIONS.contains(annotationClass)) {
				throw new IllegalArgumentException(exceptionMessage);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}