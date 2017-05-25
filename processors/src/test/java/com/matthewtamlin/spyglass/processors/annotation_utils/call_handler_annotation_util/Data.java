package com.matthewtamlin.spyglass.processors.annotation_utils.call_handler_annotation_util;

import com.matthewtamlin.java_compiler_utilities.element_supplier.ElementId;
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

import static com.matthewtamlin.spyglass.annotations.units.DimensionUnit.DP;

public class Data {
	@ElementId("specific enum")
	@SpecificEnumHandler(attributeId = 0, ordinal = 1)
	public void method1() {}

	@ElementId("specific flag")
	@SpecificFlagHandler(attributeId = 0, handledFlags = 1)
	public void method2() {}

	@ElementId("no call handler annotation")
	@DefaultToBoolean(true)
	@DefaultToBooleanResource(resId = 0)
	@DefaultToColorResource(resId = 0)
	@DefaultToColorStateListResource(resId = 0)
	@DefaultToDimension(value = 0, unit = DP)
	@DefaultToDimensionResource(resId = 0)
	@DefaultToDrawableResource(resId = 0)
	@DefaultToEnumConstant(enumClass = PlaceholderEnum.class, ordinal = 0)
	@DefaultToFloat(0)
	@DefaultToFractionResource(resId = 0, baseMultiplier = 0, parentMultiplier = 0)
	@DefaultToInteger(0)
	@DefaultToIntegerResource(resId = 0)
	@DefaultToNull
	@DefaultToString("hello world")
	@DefaultToStringResource(resId = 0)
	@DefaultToTextArrayResource(resId = 0)
	@DefaultToTextResource(0)
	@BooleanHandler(attributeId = 0)
	@ColorHandler(attributeId = 0)
	@ColorStateListHandler(attributeId = 0)
	@DimensionHandler(attributeId = 0)
	@DrawableHandler(attributeId = 0)
	@EnumConstantHandler(attributeId = 0, enumClass = PlaceholderEnum.class)
	@EnumOrdinalHandler(attributeId = 0)
	@FloatHandler(attributeId = 0)
	@FractionHandler(attributeId = 0)
	@IntegerHandler(attributeId = 0)
	@StringHandler(attributeId = 0)
	@TextArrayHandler(attributeId = 0)
	@TextHandler(attributeId = 0)
	public void method3() {}

	private enum PlaceholderEnum {}
}