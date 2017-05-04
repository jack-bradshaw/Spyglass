package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.processors.functional.ParametrisedSupplier;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.CALL_HANDLER_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.DEFAULT_ANNOTATIONS;
import static com.matthewtamlin.spyglass.processors.core.AnnotationRegistry.VALUE_HANDLER_ANNOTATIONS;
import static javax.lang.model.element.Modifier.PUBLIC;

public class CallerComponentBuilder {
	private static final Map<Class<? extends Annotation>, ParametrisedSupplier<Annotation, CodeBlock>>
			SHOULD_CALL_BOTH_SUPPLIERS;

	private static final Map<Class<? extends Annotation>, ParametrisedSupplier<Annotation, CodeBlock>>
			VALUE_IS_AVAILABLE_BODY_SUPPLIERS;

	private static final Map<Class<? extends Annotation>, ParametrisedSupplier<Annotation, CodeBlock>>
			GET_VALUE_BODY_SUPPLIERS;

	private static final Map<Class<? extends Annotation>, ParametrisedSupplier<Annotation, CodeBlock>>
			GET_DEFAULT_VALUE_BODY_SUPPLIERS;

	static {
		SHOULD_CALL_BOTH_SUPPLIERS = new HashMap<>();

		SHOULD_CALL_BOTH_SUPPLIERS.put(
				SpecificEnumHandler.class,
				new ParametrisedSupplier<Annotation, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final Annotation object) {
						final SpecificEnumHandler anno = (SpecificEnumHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", anno.attributeId())
								.addStatement("final int value2 = attrs.getInt($L, 2)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && " +
										"value2 == 2")
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

		SHOULD_CALL_BOTH_SUPPLIERS.put(
				SpecificFlagHandler.class,
				new ParametrisedSupplier<Annotation, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final Annotation object) {
						final SpecificFlagHandler anno = (SpecificFlagHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final int value1 = attrs.getInt($L, 1)", anno.attributeId())
								.addStatement("final int value2 = attrs.getInt($L, 2)", anno.attributeId())
								.add("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == 1 && " +
										"value2 == 2")
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

	static {
		VALUE_IS_AVAILABLE_BODY_SUPPLIERS = new HashMap<>();

		VALUE_IS_AVAILABLE_BODY_SUPPLIERS.put(
				BooleanHandler.class,
				new ParametrisedSupplier<Annotation, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final Annotation object) {
						final BooleanHandler anno = (BooleanHandler) object;

						return CodeBlock
								.builder()
								.addStatement("final boolean value1 = attrs.getBoolean($L, false)", anno.attributeId())
								.addStatement("final boolean value2 = attrs.getBoolean($L, true)", anno.attributeId())
								.addStatement("\n")
								.addStatement("final boolean defaultConsistentlyReturned = " +
										"value1 == false && " +
										"value2 == true")
								.addStatement("\n")
								.addStatement("return !defaultConsistentlyReturned")
								.build();
					}
				}
		);
	}

	static {
		GET_VALUE_BODY_SUPPLIERS = new HashMap<>();

		GET_VALUE_BODY_SUPPLIERS.put(
				BooleanHandler.class,
				new ParametrisedSupplier<Annotation, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final Annotation object) {
						final BooleanHandler anno = (BooleanHandler) object;

						return CodeBlock.
								builder()
								.add("return attrs.getBoolean($L, false)", anno.attributeId())
								.build();
					}
				}
		);
	}

	static {
		GET_DEFAULT_VALUE_BODY_SUPPLIERS = new HashMap<>();

		GET_DEFAULT_VALUE_BODY_SUPPLIERS.put(
				DefaultToBoolean.class,
				new ParametrisedSupplier<Annotation, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final Annotation object) {
						final DefaultToBoolean anno = (DefaultToBoolean) object;

						return CodeBlock
								.builder()
								.add("return $L", anno.value())
								.build();
					}
				}
		);
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
	public static MethodSpec buildShouldCallMethodSpecFor(final Annotation anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		if (!CALL_HANDLER_ANNOTATIONS.contains(anno.annotationType())) {
			throw new IllegalArgumentException("Argument \'anno\' must be a call handler annotation.");
		}

		final CodeBlock methodBody = SHOULD_CALL_BOTH_SUPPLIERS.get(anno.annotationType()).supplyFor(anno);

		return MethodSpec
				.methodBuilder("shouldCallMethod")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassDefinitions.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	public static MethodSpec buildValueIsAvailableSpecFor(final Annotation anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		if (!VALUE_HANDLER_ANNOTATIONS.contains(anno.annotationType())) {
			throw new IllegalArgumentException("Argument \'anno\' must be a value handler annotation.");
		}

		final CodeBlock methodBody = VALUE_IS_AVAILABLE_BODY_SUPPLIERS.get(anno.annotationType()).supplyFor(anno);

		return MethodSpec
				.methodBuilder("valueIsAvailable")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassDefinitions.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	public static MethodSpec buildGetValueSpecFor(final Annotation anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		if (!VALUE_HANDLER_ANNOTATIONS.contains(anno.annotationType())) {
			throw new IllegalArgumentException("Argument \'anno\' must be a value handler annotation.");
		}

		final CodeBlock methodBody = GET_VALUE_BODY_SUPPLIERS.get(anno.annotationType()).supplyFor(anno);

		return MethodSpec
				.methodBuilder("getValue")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassDefinitions.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}

	public static MethodSpec buildGetDefaultValueSpecFor(final Annotation anno) {
		checkNotNull(anno, "Argument \'anno\' cannot be null.");

		if (!DEFAULT_ANNOTATIONS.contains(anno.annotationType())) {
			throw new IllegalArgumentException("Argument \'anno\' must be a default annotation.");
		}

		final CodeBlock methodBody = GET_DEFAULT_VALUE_BODY_SUPPLIERS.get(anno.annotationType()).supplyFor(anno);

		return MethodSpec
				.methodBuilder("getDefaultValue")
				.addModifiers(PUBLIC)
				.returns(Object.class)
				.addParameter(AndroidClassDefinitions.CONTEXT, "context")
				.addParameter(AndroidClassDefinitions.TYPED_ARRAY, "attrs")
				.addCode(methodBody)
				.build();
	}
}