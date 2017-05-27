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
import com.matthewtamlin.spyglass.processors.annotation_utils.AnnotationMirrorUtil;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.matthewtamlin.spyglass.processors.util.EnumUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.util.Elements;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CallerComponentGenerator {
	private static final TypeName ENUM_UTIL_TYPE_NAME = ClassName.get(EnumUtil.class);

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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");
						final int ordinal = getValueFromAnnotationMirror(object, "ordinal");

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", attributeId)
								.addStatement("final int value2 = attrs.getInt($L, 2)", attributeId)
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"(value1 == 1) && (value2 == 2)")
								.add("\n")
								.beginControlFlow("if (defaultConsistentlyReturned)")
								.addStatement("return false")
								.nextControlFlow("else")
								.addStatement("return value1 == $L", ordinal)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");
						final int handledFlags = getValueFromAnnotationMirror(object, "handledFlags");

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", attributeId)
								.addStatement("final int value2 = attrs.getInt($L, 2)", attributeId)
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && value2 == 2")
								.add("\n")
								.beginControlFlow("if (defaultConsistentlyReturned)")
								.addStatement("return false")
								.nextControlFlow("else")
								.addStatement("return (value1 & $L) > 0", handledFlags)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final boolean value1 = attrs.getBoolean($L, false)", attributeId)
								.addStatement("final boolean value2 = attrs.getBoolean($L, true)", attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getColor($L, 1)", attributeId)
								.addStatement("final int value2 = attrs.getColor($L, 2)", attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getColorStateList($L) != null", attributeId)
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										attributeId)
								.addStatement("final float value2 = attrs.getDimension($L, Float.POSITIVE_INFINITY)",
										attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getDrawable($L) != null", attributeId)
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final int value1 = array.getInt($L, 1)", attributeId)
								.addStatement("final int value2 = array.getInt($L, 2)", attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final int value1 = array.getInt($L, 1)", attributeId)
								.addStatement("final int value2 = array.getInt($L, 2)", attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getFloat($L, Float.NEGATIVE_INFINITY)",
										attributeId)
								.addStatement("final float value2 = attrs.getFloat($L, Float.POSITIVE_INFINITY)",
										attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final float value1 = attrs.getFraction(" +
										"$L, 1, 1, Float.NEGATIVE_INFINITY)", attributeId)
								.addStatement("final float value2 = attrs.getFraction(" +
										"$L, 1, 1, Float.POSITIVE_INFINITY)", attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", attributeId)
								.addStatement("final int value2 = attrs.getInt($L, 2)", attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.hasValue($L)", attributeId)
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				TextArrayHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getTextArray($L) != null", attributeId)
								.build();
					}
				}
		);

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				TextHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getText($L) != null", attributeId)
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
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock.
								builder()
								.add("return attrs.getBoolean($L, false)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				ColorHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getColor($L, 1)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				ColorStateListHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getColorStateList($L)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				DimensionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getDimension($L, Float.NEGATIVE_INFINITY)",
										attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				DrawableHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getDrawable($L)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				EnumConstantHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");
						final Class<? extends Enum> enumClass = getValueFromAnnotationMirror(object, "enumClass");

						return CodeBlock
								.builder()
								.addStatement("final int ordinal = attrs.getInt($L, 0)", attributeId)
								.addStatement("return $T.getEnumConstant($L, ordinal)", ENUM_UTIL_TYPE_NAME, enumClass)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				EnumOrdinalHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return array.getInt($L, 1)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				FloatHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getFloat($L, Float.NEGATIVE_INFINITY)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				FractionHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");
						final int baseMultiplier = getValueFromAnnotationMirror(object, "baseMultiplier");
						final int parentMultiplier = getValueFromAnnotationMirror(object, "parentMultiplier");

						return CodeBlock
								.builder()
								.addStatement(
										"return attrs.getFraction($L, $L, $L, Float.NEGATIVE_INFINITY)",
										attributeId,
										baseMultiplier,
										parentMultiplier)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				IntegerHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getInt($L, 1)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				StringHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getString($L)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				TextArrayHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getTextArray($L)", attributeId)
								.build();
					}
				}
		);

		GET_VALUE_METHOD_BODY_SUPPLIERS.put(
				TextHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						final int attributeId = getValueFromAnnotationMirror(object, "attributeId");

						return CodeBlock
								.builder()
								.addStatement("return attrs.getText($L)", attributeId)
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
						final int value = getValueFromAnnotationMirror(object, "value");

						return CodeBlock
								.builder()
								.add("return $L", value)
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

	@SuppressWarnings("unchecked") // Managed externally and by tests
	private <T> T getValueFromAnnotationMirror(final AnnotationMirror mirror, final String key) {
		return (T) AnnotationMirrorUtil.getAnnotationValueWithDefaults(mirror, key, elementsUtil);
	}
}