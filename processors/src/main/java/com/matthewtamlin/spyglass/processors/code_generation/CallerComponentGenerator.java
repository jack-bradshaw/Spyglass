package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
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
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CallerComponentGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			SHOULD_CALL_METHOD_BODY_SUPPLIERS;

	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			VALUE_IS_AVAILABLE_BODY_SUPPLIERS;

	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			GET_VALUE_METHOD_BODY_SUPPLIERS;

	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>>
			GET_DEFAULT_VALUE_METHOD_BODY_SUPPLIERS;

	private final Elements elementsUtil;

	{
		SHOULD_CALL_METHOD_BODY_SUPPLIERS = new HashMap<>();

		SHOULD_CALL_METHOD_BODY_SUPPLIERS.put(
				SpecificEnumHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final SpecificEnumHandler anno = (SpecificEnumHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", anno.attributeId())
								.addStatement("final int value2 = attrs.getInt($L, 2)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"(value1 == 1) && (value2 == 2)")
								.add("\n")
								.beginControlFlow("if (defaultConsistentlyReturned)")
								.addStatement("return false")
								.nextControlFlow("else")
								.addStatement("return value1 == $L", anno.ordinal())
								.endControlFlow()
								.build();
					}
				}
		);

		SHOULD_CALL_METHOD_BODY_SUPPLIERS.put(
				SpecificFlagHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final SpecificFlagHandler anno = (SpecificFlagHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", anno.attributeId())
								.addStatement("final int value2 = attrs.getInt($L, 2)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.beginControlFlow("if (defaultConsistentlyReturned)")
								.addStatement("return false")
								.nextControlFlow("else")
								.addStatement("return (value1 & $L) > 0", anno.handledFlags())
								.endControlFlow()
								.build();
					}
				}
		);
	}

	{
		VALUE_IS_AVAILABLE_BODY_SUPPLIERS = new HashMap<>();

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final BooleanHandler anno = (BooleanHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final boolean value1 = attrs.getBoolean($L, false)", anno.attributeId())
								.addStatement("final boolean value2 = attrs.getBoolean($L, true)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == false && value2 == true")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final ColorHandler anno = (ColorHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getColor($L, 1)", anno.attributeId())
								.addStatement("final int value2 = attrs.getColor($L, 2)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final ColorStateListHandler anno = (ColorStateListHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getColorStateList($L) != null", anno.attributeId())
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final DimensionHandler anno = (DimensionHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										anno.attributeId())
								.addStatement("final float value2 = attrs.getDimension($L, Float.POSITIVE_INFINITY)",
										anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == Float.NEGATIVE_INFINITY && value2 == Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final DrawableHandler anno = (DrawableHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getDrawable($L) != null", anno.attributeId())
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final EnumConstantHandler anno = (EnumConstantHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = array.getInt($L, 1)", anno.attributeId())
								.addStatement("final int value2 = array.getInt($L, 2)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final EnumOrdinalHandler anno = (EnumOrdinalHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = array.getInt($L, 1)", anno.attributeId())
								.addStatement("final int value2 = array.getInt($L, 2)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final FloatHandler anno = (FloatHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getFloat($L, Float.NEGATIVE_INFINITY)",
										anno.attributeId())
								.addStatement("final float value2 = attrs.getFloat($L, Float.POSITIVE_INFINITY)",
										anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == Float.NEGATIVE_INFINITY && value2 == Float.POSITIVE_INFINITY")
								.add("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final FractionHandler anno = (FractionHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getFraction(" +
										"$L, 1, 1, Float.NEGATIVE_INFINITY)", anno.attributeId())
								.addStatement("final float value2 = attrs.getFraction(" +
										"$L, 1, 1, Float.POSITIVE_INFINITY)", anno.attributeId())
								.addStatement("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == Float.NEGATIVE_INFINITY && value2 == Float.POSITIVE_INFINITY")
								.addStatement("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final IntegerHandler anno = (IntegerHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", anno.attributeId())
								.addStatement("final int value2 = attrs.getInt($L, 2)", anno.attributeId())
								.addStatement("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.addStatement("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final StringHandler anno = (StringHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.hasValue($L)", anno.attributeId())
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				TextArrayHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final TextArrayHandler anno = (TextArrayHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getTextArray($L) != null", anno.attributeId())
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				TextHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final TextHandler anno = (TextHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getText($L) != null", anno.attributeId())
								.build();
					}
				}
		);
	}

	{
		GET_VALUE_METHOD_BODY_SUPPLIERS = new HashMap<>();

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				BooleanHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final BooleanHandler anno = (BooleanHandler) object;

						return CodeBlock.
								builder()
								.add("return attrs.getBoolean($L, false)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final ColorHandler anno = (ColorHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getColor($L, 1)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final ColorStateListHandler anno = (ColorStateListHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getColorStateList($L)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final DimensionHandler anno = (DimensionHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final DrawableHandler anno = (DrawableHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getDrawable($L)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final EnumConstantHandler anno = (EnumConstantHandler) object;

						//TODO
						return null;
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final EnumOrdinalHandler anno = (EnumOrdinalHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return array.getInt($L, 1)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final FloatHandler anno = (FloatHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getFloat($L, Float.NEGATIVE_INFINITY)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final FractionHandler anno = (FractionHandler) object;

						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
										anno.attributeId(),
										anno.baseMultiplier(),
										anno.parentMultiplier())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final IntegerHandler anno = (IntegerHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getInt($L, 1)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final StringHandler anno = (StringHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getString($L)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				TextArrayHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final TextArrayHandler anno = (TextArrayHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getTextArray($L)", anno.attributeId())
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				TextHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final TextHandler anno = (TextHandler) object;

						return CodeBlock
								.builder()
								.addStatement("return attrs.getText($L)", anno.attributeId())
								.build();
					}
				}
		);
	}

	{
		GET_DEFAULT_VALUE_METHOD_BODY_SUPPLIERS = new HashMap<>();

		GET_DEFAULT_VALUE_METHOD_BODY_SUPPLIERS.put(
				DefaultToBoolean.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final DefaultToBoolean anno = (DefaultToBoolean) object;

						return CodeBlock
								.builder()
								.add("return $L", anno.value())
								.build();
					}
				}
		);
	}

	public CallerComponentGenerator(final Elements elementsUtil) {
		this.elementsUtil = checkNotNull(elementsUtil, "Argument \'elementsUtil\' cannot be null.");
	}

	/**
	 * Returns a method spec equivalent to the following:
	 * <pre>{@code
	 * public boolean shouldCallMethod(TypedArray attrs) {
	 * 	// Some implementation, varies
	 * }}</pre>
	 * <p>
	 * The body is dynamically generated based on the attr checks defined by the supplied attribute.
	 *
	 * @param anno
	 * 		the annotation to base the method body on, not null
	 *
	 * @return the method spec, not null
	 *
	 * @throws IllegalArgumentException
	 * 		if {@code annotation} is null
	 */
	public MethodSpec generateShouldCallMethodSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!SHOULD_CALL_METHOD_BODY_SUPPLIERS.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a mirror of a call handler annotation.");
		}


		final CodeBlock methodBody = SHOULD_CALL_METHOD_BODY_SUPPLIERS.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("shouldCallMethod")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
				.addCode(methodBody)
				.build();
	}

	public MethodSpec generateValueIsAvailableSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!VALUE_IS_AVAILABLE_BODY_SUPPLIERS.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a mirror of a value handler annotation.");
		}

		final CodeBlock methodBody = VALUE_IS_AVAILABLE_BODY_SUPPLIERS.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("valueIsAvailable")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	public MethodSpec generateGetValueSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!GET_VALUE_METHOD_BODY_SUPPLIERS.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a mirror of a value handler annotation.");
		}

		final CodeBlock methodBody = GET_VALUE_METHOD_BODY_SUPPLIERS.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("getValue")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	public MethodSpec generateGetDefaultValueSpecFor(final AnnotationMirror anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		final String annotationTypeName = anno.getAnnotationType().toString();

		if (!GET_DEFAULT_VALUE_METHOD_BODY_SUPPLIERS.containsKey(annotationTypeName)) {
			throw new IllegalArgumentException("Argument \'anno\' must be a default annotation.");
		}

		final CodeBlock methodBody = GET_DEFAULT_VALUE_METHOD_BODY_SUPPLIERS.get(annotationTypeName).supplyFor(anno);

		return MethodSpec
				.methodBuilder("getDefaultValue")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassNames.CONTEXT, "context")
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}
}