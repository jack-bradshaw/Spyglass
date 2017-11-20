package com.matthewtamlin.spyglass.processor.annotation_retrievers.default_annotation_retriever;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificEnumHandler;
import com.matthewtamlin.spyglass.markers.annotations.call_handler_annotations.SpecificFlagHandler;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.markers.annotations.default_annotations.DefaultToTextResource;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.markers.annotations.value_handler_annotations.StringHandler;

import static com.matthewtamlin.spyglass.markers.units.DimensionUnit.DP;

public class Data {
	@ElementId("boolean")
	@DefaultToBoolean(true)
	public void method1() {}

	@ElementId("boolean resource")
	@DefaultToBooleanResource(resId = 0)
	public void method2() {}

	@ElementId("color resource")
	@DefaultToColorResource(resId = 0)
	public void method3() {}

	@ElementId("color state list resource")
	@DefaultToColorStateListResource(resId = 0)
	public void method4() {}

	@ElementId("dimension")
	@DefaultToDimension(value = 0, unit = DP)
	public void method5() {}

	@ElementId("dimension resource")
	@DefaultToDimensionResource(resId = 0)
	public void method6() {}

	@ElementId("drawable resource")
	@DefaultToDrawableResource(resId = 0)
	public void method7() {}

	@ElementId("enum constant")
	@DefaultToEnumConstant(enumClass = PlaceholderEnum.class, ordinal = 0)
	public void method8() {}

	@ElementId("float")
	@DefaultToFloat(0)
	public void method9() {}

	@ElementId("fraction resource")
	@DefaultToFractionResource(resId = 0, baseMultiplier = 0, parentMultiplier = 0)
	public void method10() {}

	@ElementId("integer")
	@DefaultToInteger(0)
	public void method11() {}

	@ElementId("integer resource")
	@DefaultToIntegerResource(resId = 0)
	public void method12() {}

	@ElementId("null")
	@DefaultToNull()
	public void method13() {}

	@ElementId("string")
	@DefaultToString("hello world")
	public void method14() {}

	@ElementId("string resource")
	@DefaultToStringResource(resId = 0)
	public void method15() {}

	@ElementId("text array resource")
	@DefaultToTextArrayResource(resId = 0)
	public void method16() {}

	@ElementId("text resource")
	@DefaultToTextResource(resId = 0)
	public void method17() {}

	@ElementId("no default annotation")
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
	@SpecificEnumHandler(attributeId = 0, handledOrdinal = 0)
	@SpecificFlagHandler(attributeId = 0, handledFlags = 0)
	public void method18() {}

	private enum PlaceholderEnum {}
}