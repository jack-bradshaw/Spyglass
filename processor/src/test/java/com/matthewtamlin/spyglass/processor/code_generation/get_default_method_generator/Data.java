package com.matthewtamlin.spyglass.processor.code_generation.get_default_method_generator;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToBooleanResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToColorResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToColorStateListResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToDimension;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToDimensionResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToDrawableResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToEnumConstant;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToFloat;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToFractionResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToInteger;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToIntegerResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToNull;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToString;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToStringResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToTextArrayResource;
import com.matthewtamlin.spyglass.annotations.annotations.default_annotations.DefaultToTextResource;

import static com.matthewtamlin.spyglass.annotations.units.DimensionUnit.DP;

public class Data {
	@ElementId("boolean")
	@DefaultToBoolean(true)
	public void withBoolean() {}

	@ElementId("boolean resource")
	@DefaultToBooleanResource(resId = 1)
	public void withBooleanRes() {}

	@ElementId("color resource")
	@DefaultToColorResource(resId = 1)
	public void withColorRes() {}

	@ElementId("color state list resource")
	@DefaultToColorStateListResource(resId = 1)
	public void withColorStateListRes() {}

	@ElementId("dimension")
	@DefaultToDimension(value = 1, unit = DP)
	public void withDimension() {}

	@ElementId("dimension resource")
	@DefaultToDimensionResource(resId = 1)
	public void withDimensionRes() {}

	@ElementId("drawable resource")
	@DefaultToDrawableResource(resId = 1)
	public void withDrawableRes() {}

	@ElementId("enum constant")
	@DefaultToEnumConstant(enumClass = PlaceholderEnum.class, ordinal = 0)
	public void withEnumConstant() {}

	@ElementId("float")
	@DefaultToFloat(1F)
	public void withFloat() {}

	@ElementId("fraction resource")
	@DefaultToFractionResource(resId = 1)
	public void withFractionRes() {}

	@ElementId("integer")
	@DefaultToInteger(1)
	public void withInteger() {}

	@ElementId("integer resource")
	@DefaultToIntegerResource(resId = 1)
	public void withIntegerRes() {}

	@ElementId("null")
	@DefaultToNull
	public void withNull() {}

	@ElementId("string")
	@DefaultToString("")
	public void withString() {}

	@ElementId("string resource")
	@DefaultToStringResource(resId = 1)
	public void withStringRes() {}

	@ElementId("text array")
	@DefaultToTextArrayResource(resId = 1)
	public void withTextArrayRes() {}

	@ElementId("text")
	@DefaultToTextResource(resId = 1)
	public void withTextRes() {}

	public enum PlaceholderEnum {}
}