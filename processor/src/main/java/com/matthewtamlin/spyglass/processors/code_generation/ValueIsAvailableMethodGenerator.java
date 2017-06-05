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
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil.getAnnotationValueWithDefaults;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class ValueIsAvailableMethodGenerator {
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
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getBoolean($1L, false) == false && \n" +
												"attrs.getBoolean($1L, true) == true",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getColor($1L, 1) == 1 && \n" +
												"attrs.getColor($1L, 2) == 2",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
										"return attrs.getColorStateList($L) != null",
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
								.addStatement("final float negInf = Float.NEGATIVE_INFINITY")
								.addStatement("final float posInf = Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getDimension($1L, negInf) == negInf && \n" +
												"attrs.getDimension($1L, posInf) == posInf",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
										"return attrs.getDrawable($L) != null",
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
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"array.getInt($1L, 1) == 1 && \n" +
												"array.getInt($1L, 2) == 2",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
										"final boolean defaultConsistentlyReturned = \n" +
												"array.getInt($1L, 1) == 1 && \n" +
												"array.getInt($1L, 2) == 2",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
								.addStatement("final float negInf = Float.NEGATIVE_INFINITY")
								.addStatement("final float posInf = Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getFloat($1L, negInf) == negInf && \n" +
												"attrs.getFloat($1L, posInf) == posInf",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
								.addStatement("final float negInf = Float.NEGATIVE_INFINITY")
								.addStatement("final float posInf = Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getFraction($1L, 1, 1, negInf) == negInf && \n" +
												"attrs.getFraction($1L, 1, 1, posInf) == posInf",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getInt($1L, 1) == 1 && \n" +
												"attrs.getInt($1L, 2) == 2",
										getLiteralFromAnnotation(anno, "attributeId"))
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
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
										"return attrs.hasValue($L)",
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
										"return attrs.getTextArray($L) != null",
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
										"return attrs.getText($L) != null",
										getLiteralFromAnnotation(anno, "attributeId"))
								.build();
					}
				}
		);
	}

	public ValueIsAvailableMethodGenerator(final Elements elementUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
	}

	/**
	 * Creates a method spec equivalent to the following:
	 * <pre>{@code
	 * public boolean valueIsAvailable(final TypedArray attrs) {
	 * 	dynamic implementation here
	 * }}</pre>
	 * <p>
	 * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
	 * queries the supplied typed array to determine if any value is available for some attribute. The method returns
	 * true if a value is available, and false otherwise. What exactly it means for a value to be available and which
	 * value is of interest is defined by each specific implementation.
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
				.methodBuilder("valueIsAvailable")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
				.addCode(methodBodySuppliers.get(annotationType).supplyFor(anno))
				.build();
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return getAnnotationValueWithDefaults(mirror, key, elementUtil).toString();
	}
}