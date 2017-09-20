package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.processor.definitions.AndroidClassNames;
import com.matthewtamlin.spyglass.processor.definitions.CallerDef;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

@Tested(testMethod = "automated")
public class GetDefaultMethodGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, MethodSpec>> methodSpecSuppliers;

	private final AnnotationMirrorHelper annotationMirrorHelper;

	{
		methodSpecSuppliers = new HashMap<>();

		methodSpecSuppliers.put(
				DefaultToBoolean.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();

						return getBaseMethodSpec()
								.returns(Boolean.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToBooleanResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getResources().getBoolean($L)",
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(Boolean.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToColorResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $T.getColor($N(), $L)",
										AndroidClassNames.CONTEXT_COMPAT,
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToColorStateListResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $T.getColorStateList($N(), $L)",
										AndroidClassNames.CONTEXT_COMPAT,
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(AndroidClassNames.COLOR_STATE_LIST)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToDimension.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final String unitFull = getLiteralFromAnnotation(anno, "unit");
						final String unitShort = unitFull.substring(unitFull.lastIndexOf(".") + 1);

						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"$T metrics = $N().getResources().getDisplayMetrics()",
										AndroidClassNames.DISPLAY_METRICS,
										CallerDef.GET_CONTEXT)
								.addStatement(
										"return $1T.applyDimension($1T.$2L, $3L, metrics)",
										AndroidClassNames.TYPED_VALUE,
										getComplexUnitLiteral(unitShort),
										getLiteralFromAnnotation(anno, "value"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToDimensionResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getResources().getDimension($L)",
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToDrawableResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $T.getDrawable($N(), $L)",
										AndroidClassNames.CONTEXT_COMPAT,
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(AndroidClassNames.DRAWABLE)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToEnumConstant.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final String enumClass = getLiteralFromAnnotation(anno, "enumClass");
						final String enumClassName = enumClass.substring(0, enumClass.lastIndexOf(".class"));

						final CodeBlock body = CodeBlock
								.builder()
								.addStatement("final int ordinal = $L", getLiteralFromAnnotation(anno, "ordinal"))
								.add("\n")
								.beginControlFlow(
										"if (ordinal < 0 || $1T.values().length - 1 < ordinal)",
										ClassName.bestGuess(enumClassName))
								.addStatement(
										"throw new $T($L)",
										RuntimeException.class,
										"\"Ordinal \" + ordinal + \" is out of bounds for enum " + enumClassName + "\"")
								.endControlFlow()
								.add("\n")
								.addStatement(
										"return $T.values()[ordinal]",
										ClassName.bestGuess(enumClassName))
								.build();

						return getBaseMethodSpec()
								.returns(ClassName.bestGuess(enumClassName))
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToFloat.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToFractionResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getResources().getFraction($L, $L, $L)",
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"),
										getLiteralFromAnnotation(anno, "baseMultiplier"),
										getLiteralFromAnnotation(anno, "parentMultiplier"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToInteger.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToIntegerResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getResources().getInteger($L)",
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(Number.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToNull.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement("return null")
								.build();

						return getBaseMethodSpec()
								.returns(Object.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToString.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement("return $L", getLiteralFromAnnotation(anno, "value"))
								.build();

						return getBaseMethodSpec()
								.returns(String.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToStringResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getResources().getString($L)",
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(String.class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToTextArrayResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getResources().getTextArray($L)",
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(CharSequence[].class)
								.addCode(body)
								.build();
					}
				}
		);

		methodSpecSuppliers.put(
				DefaultToTextResource.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return $N().getResources().getText($L)",
										CallerDef.GET_CONTEXT,
										getLiteralFromAnnotation(anno, "resId"))
								.build();

						return getBaseMethodSpec()
								.returns(CharSequence.class)
								.addCode(body)
								.build();
					}
				}
		);
	}

	public GetDefaultMethodGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		annotationMirrorHelper = coreHelpers.getAnnotationMirrorHelper();
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
	 * @param defaultAnno
	 * 		the annotation to use when generating the method body, not null
	 *
	 * @return the method spec, not null
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code defaultAnno} is null
	 * @throws IllegalArgumentException
	 * 		if {@code defaultAnno} is not a default annotation
	 */
	public MethodSpec generateFor(final AnnotationMirror defaultAnno) {
		checkNotNull(defaultAnno, "Argument \'defaultAnno\' cannot be null.");

		final String annoClassName = defaultAnno.getAnnotationType().toString();

		if (!methodSpecSuppliers.containsKey(annoClassName)) {
			throw new IllegalArgumentException("Argument \'defaultAnno\' is not a default annotation.");
		}

		return methodSpecSuppliers.get(annoClassName).supplyFor(defaultAnno);
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return annotationMirrorHelper.getValueUsingDefaults(mirror, key).toString();
	}

	private String getComplexUnitLiteral(final String unit) {
		switch (unit) {
			case "PX": {return "COMPLEX_UNIT_PX";}
			case "DP": {return "COMPLEX_UNIT_DIP";}
			case "PT": {return "COMPLEX_UNIT_PT";}
			case "IN": {return "COMPLEX_UNIT_IN";}
			case "SP": {return "COMPLEX_UNIT_SP";}
			case "MM": {return "COMPLEX_UNIT_MM";}
		}

		throw new RuntimeException("Should never get here.");
	}

	private MethodSpec.Builder getBaseMethodSpec() {
		return MethodSpec.methodBuilder("getDefault");
	}
}