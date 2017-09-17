package com.matthewtamlin.spyglass.processor.code_generation;

import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.common.annotations.use_annotations.UseString;
import com.matthewtamlin.spyglass.processor.core.CoreHelpers;
import com.matthewtamlin.spyglass.processor.mirror_helpers.AnnotationMirrorHelper;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;

import static com.matthewtamlin.java_utilities.checkers.IntChecker.checkGreaterThanOrEqualTo;
import static com.matthewtamlin.java_utilities.checkers.NullChecker.checkNotNull;

public class GetPlaceholderMethodGenerator {
	private final Map<String, MethodSpecSupplier> methodSpecSuppliers = new HashMap<>();

	private AnnotationMirrorHelper annoMirrorHelper;

	{
		methodSpecSuppliers.put(UseByte.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
		methodSpecSuppliers.put(UseDouble.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
		methodSpecSuppliers.put(UseFloat.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
		methodSpecSuppliers.put(UseInt.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
		methodSpecSuppliers.put(UseLong.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());
		methodSpecSuppliers.put(UseShort.class.getCanonicalName(), new PrimitiveToNumberMethodSpecSupplier());

		methodSpecSuppliers.put(
				UseBoolean.class.getCanonicalName(),
				new MethodSpecSupplier() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
						final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");

						return getBaseMethodSpec(position)
								.returns(Boolean.class)
								.addCode(CodeBlock
										.builder()
										.addStatement("return $L", rawValue.toString())
										.build())
								.build();
					}
				});
		methodSpecSuppliers.put(
				UseChar.class.getCanonicalName(),
				new MethodSpecSupplier() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
						final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");

						return getBaseMethodSpec(position)
								.returns(Character.class)
								.addCode(CodeBlock
										.builder()
										.addStatement("return ($T) $L", Character.class, rawValue.toString())
										.build())
								.build();
					}
				});


		methodSpecSuppliers.put(
				UseNull.class.getCanonicalName(),
				new MethodSpecSupplier() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
						return getBaseMethodSpec(position)
								.returns(Object.class)
								.addCode(CodeBlock
										.builder()
										.addStatement("return null")
										.build())
								.build();
					}
				});

		methodSpecSuppliers.put(
				UseString.class.getCanonicalName(),
				new MethodSpecSupplier() {
					@Override
					public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
						final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");

						return getBaseMethodSpec(position)
								.returns(String.class)
								.addCode(CodeBlock
										.builder()
										.addStatement("return ($T) $L", String.class, rawValue.toString())
										.build())
								.build();
					}
				}
		);
	}

	public GetPlaceholderMethodGenerator(final CoreHelpers coreHelpers) {
		checkNotNull(coreHelpers, "Argument \'coreHelpers\' cannot be null.");

		this.annoMirrorHelper = coreHelpers.getAnnotationMirrorHelper();
	}

	public MethodSpec generateFor(final AnnotationMirror useAnno, final int parameterIndex) {
		checkNotNull(useAnno, "Argument \'useAnno\' cannot be null.");
		checkGreaterThanOrEqualTo(parameterIndex, 0, "Argument \'parameterIndex\' must be at least zero.");

		final String annoClassName = useAnno.getAnnotationType().toString();

		if (!methodSpecSuppliers.containsKey(annoClassName)) {
			throw new IllegalArgumentException("Argument \'useAnno\' is not a use annotation.");
		}

		return methodSpecSuppliers.get(annoClassName).supplyFor(useAnno, parameterIndex);
	}

	private MethodSpec.Builder getBaseMethodSpec(final int position) {
		return MethodSpec.methodBuilder("getArgument" + position);
	}

	private interface MethodSpecSupplier {
		public MethodSpec supplyFor(AnnotationMirror useAnno, int position);
	}

	private class PrimitiveToNumberMethodSpecSupplier implements MethodSpecSupplier {
		@Override
		public MethodSpec supplyFor(final AnnotationMirror useAnno, final int position) {
			final AnnotationValue rawValue = annoMirrorHelper.getValueUsingDefaults(useAnno, "value");

			return getBaseMethodSpec(position)
					.returns(Number.class)
					.addCode(CodeBlock
							.builder()
							.addStatement("return $L", rawValue.toString())
							.build())
					.build();
		}
	}
}