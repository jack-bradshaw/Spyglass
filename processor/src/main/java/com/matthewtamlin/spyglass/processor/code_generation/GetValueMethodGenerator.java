package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.java_utilities.testing.Tested;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.common.enum_util.EnumUtil;
import com.matthewtamlin.spyglass.processor.core.AnnotationRegistry;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processor.mirror_utils.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.Modifier.FINAL;

@Tested(testMethod = "automated")
public class GetValueMethodGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, MethodSpec>> methodSpecSuppliers;

	private final AnnotationMirrorHelper annotationMirrorHelper;

	{
		methodSpecSuppliers = new HashMap<>();

		methodSpecSuppliers.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getBoolean($L, false)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(boolean.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getColor($L, 1)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(int.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getColorStateList($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(AndroidClassNames.COLOR_STATE_LIST)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(float.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getDrawable($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(AndroidClassNames.DRAWABLE)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final String enumClassPath = getLiteralFromAnnotation(anno, "enumClass");

						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"final int ordinal = attrs.getInt($L, 0)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.addStatement(
										"return $T.getEnumConstant($L, ordinal)",
										TypeName.get(EnumUtil.class),
										enumClassPath)
								.build();

						return getBaseMethodSpec()
								.returns(Object.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getInt($L, 1)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(int.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getFloat($L, Float.NEGATIVE_INFINITY)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(float.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
										getLiteralFromAnnotation(anno, "attributeId"),
										getLiteralFromAnnotation(anno, "baseMultiplier"),
										getLiteralFromAnnotation(anno, "parentMultiplier"))
								.build();

						return getBaseMethodSpec()
								.returns(float.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getInt($L, 1)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(int.class)
								.addCode(body).build();
					}
				}
		);

		methodSpecSuppliers.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, MethodSpec>() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror anno) {
						final CodeBlock body = CodeBlock
								.builder()
								.addStatement(
										"return attrs.getString($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();

						return getBaseMethodSpec()
								.returns(String.class)
								.addCode(body).build();
					}
				}
		);
	}

	public GetValueMethodGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		annotationMirrorHelper = coreHelpers.getAnnotationMirrorHelper();
	}

	/**
	 * Creates a method spec equivalent to the following:
	 * <pre>{@code
	 * public Object getValue(final TypedArray attrs) {
	 * 	dynamic implementation here
	 * }}</pre>
	 * <p>
	 * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
	 * queries the supplied typed array and returns a value from it. Exactly what is returned is determined by each
	 * specific implementation.
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
		checkIsValueHandlerAnnotation(anno, "Argument \'anno\' must be a mirror of a value handler annotation.");

		final String annotationType = anno.getAnnotationType().toString();
		return methodSpecSuppliers.get(annotationType).supplyFor(anno);
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return annotationMirrorHelper.getAnnotationValueWithDefaults(mirror, key).toString();
	}

	private void checkIsValueHandlerAnnotation(final AnnotationMirror anno, final String exceptionMessage) {
		try {
			final Class annotationClass = (Class) Class.forName(anno.getAnnotationType().toString());

			if (!AnnotationRegistry.VALUE_HANDLER_ANNOS.contains(annotationClass)) {
				throw new IllegalArgumentException(exceptionMessage);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private MethodSpec.Builder getBaseMethodSpec() {
		return MethodSpec
				.methodBuilder("getValue")
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL);
	}
}