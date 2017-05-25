package com.matthewtamlin.spyglass.processors.testing_utils;

import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.annotations.units.DimensionUnit;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseBoolean;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseByte;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseChar;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseDouble;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseFloat;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseInt;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseLong;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseNull;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseShort;
import com.matthewtamlin.spyglass.annotations.use_annotations.UseString;
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
	public static SpecificEnumHandler createMockSpecificEnumHandler(final int attributeId, final int ordinal) {
		final SpecificEnumHandler anno = mock(SpecificEnumHandler.class);

		doReturn(SpecificEnumHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);
		when(anno.ordinal()).thenReturn(ordinal);

		return anno;
	}

	public static SpecificFlagHandler createMockSpecificFlagHandler(final int attributeId, final int handledFlags) {
		final SpecificFlagHandler anno = mock(SpecificFlagHandler.class);

		doReturn(SpecificFlagHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);
		when(anno.handledFlags()).thenReturn(handledFlags);

		return anno;
	}

	public static BooleanHandler createMockBooleanHandler(final int attributeId) {
		final BooleanHandler anno = mock(BooleanHandler.class);

		doReturn(BooleanHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static ColorHandler createMockColorHandler(final int attributeId) {
		final ColorHandler anno = mock(ColorHandler.class);

		doReturn(ColorHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static ColorStateListHandler createMockColorStateListHandler(final int attributeId) {
		final ColorStateListHandler anno = mock(ColorStateListHandler.class);

		doReturn(ColorStateListHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static DimensionHandler createMockDimensionHandler(final int attributeId) {
		final DimensionHandler anno = mock(DimensionHandler.class);

		doReturn(DimensionHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static DrawableHandler createMockDrawableHandler(final int attributeId) {
		final DrawableHandler anno = mock(DrawableHandler.class);

		doReturn(DrawableHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static EnumConstantHandler createMockEnumConstantHandler(
			final int attributeId,
			final Class<? extends Enum> enumClass) {

		final EnumConstantHandler anno = mock(EnumConstantHandler.class);

		doReturn(EnumConstantHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);
		doReturn(enumClass).when(anno).enumClass();

		return anno;
	}

	public static EnumOrdinalHandler createMockEnumOrdinalHandler(final int attributeId) {
		final EnumOrdinalHandler anno = mock(EnumOrdinalHandler.class);

		doReturn(EnumOrdinalHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static FloatHandler createMockFloatHandler(final int attributeId) {
		final FloatHandler anno = mock(FloatHandler.class);

		doReturn(FloatHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static FractionHandler createMockFractionHandler(
			final int attributeId,
			final int baseMultiplier,
			final int parentMultiplier) {

		final FractionHandler anno = mock(FractionHandler.class);

		doReturn(FractionHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);
		when(anno.baseMultiplier()).thenReturn(baseMultiplier);
		when(anno.parentMultiplier()).thenReturn(parentMultiplier);

		return anno;
	}

	public static IntegerHandler createMockIntegerHandler(final int attributeId) {
		final IntegerHandler anno = mock(IntegerHandler.class);

		doReturn(IntegerHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static StringHandler createMockStringHandler(final int attributeId) {
		final StringHandler anno = mock(StringHandler.class);

		doReturn(StringHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static TextArrayHandler createMockTextArrayHandler(final int attributeId) {
		final TextArrayHandler anno = mock(TextArrayHandler.class);

		doReturn(TextArrayHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static TextHandler createMockTextHandler(final int attributeId) {
		final TextHandler anno = mock(TextHandler.class);

		doReturn(TextHandler.class).when(anno).annotationType();
		when(anno.attributeId()).thenReturn(attributeId);

		return anno;
	}

	public static DefaultToBoolean createMockDefaultToBoolean(final boolean value) {
		final DefaultToBoolean anno = mock(DefaultToBoolean.class);

		doReturn(DefaultToBoolean.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static DefaultToBooleanResource createMockDefaultToBooleanResource(final int value) {
		final DefaultToBooleanResource anno = mock(DefaultToBooleanResource.class);

		doReturn(DefaultToBooleanResource.class).when(anno).annotationType();
		when(anno.resId()).thenReturn(value);

		return anno;
	}

	public static DefaultToColorResource createMockDefaultToColorResource(final int value) {
		final DefaultToColorResource anno = mock(DefaultToColorResource.class);

		doReturn(DefaultToColorResource.class).when(anno).annotationType();
		when(anno.resId()).thenReturn(value);

		return anno;
	}

	public static DefaultToColorStateListResource createMockDefaultToColorStateListResource(final int value) {
		final DefaultToColorStateListResource anno = mock(DefaultToColorStateListResource.class);

		doReturn(DefaultToColorStateListResource.class).when(anno).annotationType();
		when(anno.resId()).thenReturn(value);

		return anno;
	}

	public static DefaultToDimension createMockDefaultToDimension(final float value, final DimensionUnit unit) {
		final DefaultToDimension anno = mock(DefaultToDimension.class);

		doReturn(DefaultToDimension.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);
		when(anno.unit()).thenReturn(unit);

		return anno;
	}

	public static DefaultToDimensionResource createMockDefaultToDimensionResource(final int value) {
		final DefaultToDimensionResource anno = mock(DefaultToDimensionResource.class);

		doReturn(DefaultToDimensionResource.class).when(anno).annotationType();
		when(anno.resId()).thenReturn(value);

		return anno;
	}

	public static DefaultToDrawableResource createMockDefaultToDrawableResource(final int value) {
		final DefaultToDrawableResource anno = mock(DefaultToDrawableResource.class);

		doReturn(DefaultToDrawableResource.class).when(anno).annotationType();
		when(anno.resId()).thenReturn(value);

		return anno;
	}

	public static DefaultToEnumConstant createMockDefaultToEnumConstant(
			Class<? extends Enum> enumClass,
			final int ordinal) {

		final DefaultToEnumConstant anno = mock(DefaultToEnumConstant.class);

		doReturn(DefaultToEnumConstant.class).when(anno).annotationType();
		doReturn(enumClass).when(anno).enumClass();
		when(anno.ordinal()).thenReturn(ordinal);

		return anno;
	}

	public static DefaultToFloat createMockDefaultToFloat(final float value) {
		final DefaultToFloat anno = mock(DefaultToFloat.class);

		doReturn(DefaultToFloat.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static DefaultToFractionResource createMockDefaultToFractionResource(
			final int resId,
			final int baseMultiplier,
			final int parentMultiplier) {

		final DefaultToFractionResource anno = mock(DefaultToFractionResource.class);

		doReturn(DefaultToFractionResource.class).when(anno).annotationType();
		when(anno.resId()).thenReturn(resId);
		when(anno.baseMultiplier()).thenReturn(baseMultiplier);
		when(anno.parentMultiplier()).thenReturn(parentMultiplier);

		return anno;
	}

	public static DefaultToInteger createMockDefaultToInteger(final int value) {
		final DefaultToInteger anno = mock(DefaultToInteger.class);

		doReturn(DefaultToInteger.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static DefaultToIntegerResource createMockDefaultToIntegerResource(final int value) {
		final DefaultToIntegerResource anno = mock(DefaultToIntegerResource.class);

		doReturn(DefaultToIntegerResource.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static DefaultToNull createMockDefaultToNull() {
		final DefaultToNull anno = mock(DefaultToNull.class);

		doReturn(DefaultToNull.class).when(anno).annotationType();

		return anno;
	}

	public static DefaultToString createMockDefaultToString(final String value) {
		final DefaultToString anno = mock(DefaultToString.class);

		doReturn(DefaultToString.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static DefaultToStringResource createMockDefaultToStringResource(final int value) {
		final DefaultToStringResource anno = mock(DefaultToStringResource.class);

		doReturn(DefaultToStringResource.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static DefaultToTextArrayResource createMockDefaultToTextArrayResource(final int value) {
		final DefaultToTextArrayResource anno = mock(DefaultToTextArrayResource.class);

		doReturn(DefaultToTextArrayResource.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static DefaultToTextResource createMockDefaultToTextResource(final int value) {
		final DefaultToTextResource anno = mock(DefaultToTextResource.class);

		doReturn(DefaultToTextResource.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseBoolean createMockUseBoolean(final boolean value) {
		final UseBoolean anno = mock(UseBoolean.class);

		doReturn(UseBoolean.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseByte createMockUseByte(final byte value) {
		final UseByte anno = mock(UseByte.class);

		doReturn(UseByte.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseChar createMockUseChar(final char value) {
		final UseChar anno = mock(UseChar.class);

		doReturn(UseChar.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseDouble createMockUseDouble(final double value) {
		final UseDouble anno = mock(UseDouble.class);

		doReturn(UseDouble.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseFloat createMockUseFloat(final float value) {
		final UseFloat anno = mock(UseFloat.class);

		doReturn(UseFloat.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseInt createMockUseInt(final int value) {
		final UseInt anno = mock(UseInt.class);

		doReturn(UseInt.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseLong createMockUseLong(final long value) {
		final UseLong anno = mock(UseLong.class);

		doReturn(UseLong.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseNull createMockUseNull() {
		final UseNull anno = mock(UseNull.class);

		doReturn(UseNull.class).when(anno).annotationType();

		return anno;
	}

	public static UseShort createMockUseShort(final short value) {
		final UseShort anno = mock(UseShort.class);

		doReturn(UseShort.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}

	public static UseString createMockUseString(final String value) {
		final UseString anno = mock(UseString.class);

		doReturn(UseString.class).when(anno).annotationType();
		when(anno.value()).thenReturn(value);

		return anno;
	}
}