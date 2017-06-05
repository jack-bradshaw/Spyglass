package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.spyglass.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.StringHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.TextArrayHandler;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.TextHandler;
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

public class GetValueMethodGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>> methodBodySuppliers;

	private final Elements elementUtil;

	{
		methodBodySuppliers = new HashMap<>();

		methodBodySuppliers.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getBoolean($L, false)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getColor($L, 1)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getColorStateList($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getDrawable($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						final String enumClass = getLiteralFromAnnotation(anno, "enumClass");

						return CodeBlock
								.builder()
								.addStatement(
										"final int ordinal = attrs.getInt($L, 0)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.addStatement(
										"return $T.getEnumConstant($L, ordinal)",
										TypeName.get(EnumUtil.class),
										enumClass)
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return array.getInt($L, 1)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getFloat($L, Float.NEGATIVE_INFINITY)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
										getLiteralFromAnnotation(anno, "attributeId"),
										getLiteralFromAnnotation(anno, "baseMultiplier"),
										getLiteralFromAnnotation(anno, "parentMultiplier"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getInt($L, 1)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getString($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				TextArrayHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getTextArray($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				TextHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror anno) {
						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getText($L)",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);
	}

	public GetValueMethodGenerator(final Elements elementUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
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

		final String annotationType = anno.getAnnotationType().toString();

		return MethodSpec
				.methodBuilder("getValue")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
				.addCode(methodBodySuppliers.get(annotationType).supplyFor(anno))
				.build();
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return getAnnotationValueWithDefaults(mirror, key, elementUtil).toString();
	}
}