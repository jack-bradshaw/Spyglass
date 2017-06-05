package com.matthewtamlin.spyglass.processors.code_generation;

import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
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

public class ShouldDoInvocationMethodGenerator {
	private final Map<String, ParametrisedSupplier<AnnotationMirror, CodeBlock>> methodBodySuppliers;

	private final Elements elementUtil;

	{
		methodBodySuppliers = new HashMap<>();

		methodBodySuppliers.put(
				SpecificEnumHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getInt($1L, 1) == 1 && \n" +
												"attrs.getInt($1L, 2) == 2",
										getLiteralFromAnnotation(object, "attributeId"))
								.add("\n")
								.addStatement(
										"return defaultConsistentlyReturned ? \n" +
												"false :\n" +
												"attrs.getInt($L, 0) == $L",
										getLiteralFromAnnotation(object, "attributeId"),
										getLiteralFromAnnotation(object, "ordinal"))
								.build();
					}
				}
		);

		methodBodySuppliers.put(
				SpecificFlagHandler.class.getName(),
				new ParametrisedSupplier<AnnotationMirror, CodeBlock>() {
					@Override
					public CodeBlock supplyFor(final AnnotationMirror object) {
						return CodeBlock
								.builder()
								.addStatement(
										"final boolean defaultConsistentlyReturned = \n" +
												"attrs.getInt($1L, 1) == 1 && \n" +
												"attrs.getInt($1L, 2) == 2",
										getLiteralFromAnnotation(object, "attributeId"))
								.add("\n")
								.addStatement(
										"return defaultConsistentlyReturned ? \n" +
												"false : \n" +
												"(attrs.getInt($L, 0) & $L) > 0",
										getLiteralFromAnnotation(object, "attributeId"),
										getLiteralFromAnnotation(object, "handledFlags"))
								.build();
					}
				}
		);
	}

	public ShouldDoInvocationMethodGenerator(final Elements elementUtil) {
		this.elementUtil = checkNotNull(elementUtil, "Argument \'elementUtil\' cannot be null.");
	}

	/**
	 * Creates a method spec equivalent to the following:
	 * <pre>{@code
	 * public boolean shouldDoInvocation(final TypedArray attrs) {
	 * 	dynamic implementation here
	 * }}</pre>
	 * <p>
	 * The body of the method is dynamically generated based on the supplied annotation. In general terms, the method
	 * queries the supplied typed array to determine if a specific value is available. The method returns true if the
	 * value is available, and false otherwise. What exactly it means for a value to be available and which value is
	 * of interest is defined by each specific implementation.
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

		final String annotationTypeName = anno.getAnnotationType().toString();

		return MethodSpec
				.methodBuilder("shouldDoInvocation")
				.addModifiers(PUBLIC)
				.returns(boolean.class)
				.addParameter(AndroidClassNames.TYPED_ARRAY, "attrs", FINAL)
				.addCode(methodBodySuppliers.get(annotationTypeName).supplyFor(anno))
				.build();
	}

	private String getLiteralFromAnnotation(final AnnotationMirror mirror, final String key) {
		return getAnnotationValueWithDefaults(mirror, key, elementUtil).toString();
	}
}