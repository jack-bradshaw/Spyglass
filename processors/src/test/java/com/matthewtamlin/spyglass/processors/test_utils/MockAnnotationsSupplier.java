package com.matthewtamlin.spyglass.processors.test_utils;

import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockAnnotationsSupplier {
	private SpecificEnumHandler createMockSpecificEnumHandler(final int id, final int ordinal) {
		final SpecificEnumHandler anno = mock(SpecificEnumHandler.class);

		doReturn(SpecificEnumHandler.class).when(anno).annotationType();

		when(anno.attributeId()).thenReturn(id);
		when(anno.ordinal()).thenReturn(ordinal);

		return anno;
	}

	private SpecificFlagHandler createMockSpecificFlagHandler(final int id, final int handledFlags) {
		final SpecificFlagHandler anno = mock(SpecificFlagHandler.class);

		doReturn(SpecificFlagHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);
		when(anno.handledFlags()).thenReturn(handledFlags);

		return anno;
	}

	private BooleanHandler createMockBooleanHandler(final int id) {
		final BooleanHandler anno = mock(BooleanHandler.class);

		doReturn(BooleanHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private ColorHandler createMockColorHandler(final int id) {
		final ColorHandler anno = mock(ColorHandler.class);

		doReturn(ColorHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private ColorStateListHandler createMockColorStateListHandler(final int id) {
		final ColorStateListHandler anno = mock(ColorStateListHandler.class);

		doReturn(ColorStateListHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private DimensionHandler createMockDimensionHandler(final int id) {
		final DimensionHandler anno = mock(DimensionHandler.class);

		doReturn(DimensionHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private DrawableHandler createMockDrawableHandler(final int id) {
		final DrawableHandler anno = mock(DrawableHandler.class);

		doReturn(DrawableHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private EnumConstantHandler createMockEnumConstantHandler(final int id, final Class<? extends Enum> enumClass) {
		final EnumConstantHandler anno = mock(EnumConstantHandler.class);

		doReturn(EnumConstantHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);
		doReturn(enumClass).when(anno.enumClass());

		return anno;
	}

	private EnumOrdinalHandler createMockEnumOrdinalHandler(final int id) {
		final EnumOrdinalHandler anno = mock(EnumOrdinalHandler.class);

		doReturn(EnumOrdinalHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private FloatHandler createMockFloatHandler(final int id) {
		final FloatHandler anno = mock(FloatHandler.class);

		doReturn(FloatHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private FractionHandler createMockFractionHandler(
			final int id,
			final int baseMultiplier,
			final int parentMultiplier) {

		final FractionHandler anno = mock(FractionHandler.class);

		doReturn(FractionHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);
		when(anno.baseMultiplier()).thenReturn(baseMultiplier);
		when(anno.parentMultiplier()).thenReturn(parentMultiplier);

		return anno;
	}

	private IntegerHandler createMockIntegerHandler(final int id) {
		final IntegerHandler anno = mock(IntegerHandler.class);

		doReturn(IntegerHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private StringHandler createMockStringHandler(final int id) {
		final StringHandler anno = mock(StringHandler.class);

		doReturn(StringHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private TextArrayHandler createMockTextArrayHandler(final int id) {
		final TextArrayHandler anno = mock(TextArrayHandler.class);

		doReturn(TextArrayHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}

	private TextHandler createMockTextHandler(final int id) {
		final TextHandler anno = mock(TextHandler.class);

		doReturn(TextHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(id);

		return anno;
	}
}