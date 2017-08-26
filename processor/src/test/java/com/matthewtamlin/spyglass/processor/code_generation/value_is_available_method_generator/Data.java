package com.matthewtamlin.spyglass.processor.code_generation.value_is_available_method_generator;

import com.matthewtamlin.avatar.rules.ElementId;
import com.matthewtamlin.spyglass.common.annotations.default_annotations.DefaultToBoolean;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.BooleanHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.ColorStateListHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DimensionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.DrawableHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumConstantHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.EnumOrdinalHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FloatHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.FractionHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.IntegerHandler;
import com.matthewtamlin.spyglass.common.annotations.value_handler_annotations.StringHandler;

public class Data {
	@ElementId("no value handler")
	@DefaultToBoolean(true)
	public void noValueHandlerAnnotation() {}

	@ElementId("boolean")
	@BooleanHandler(attributeId = 1)
	public void withBoolean() {}

	@ElementId("color")
	@ColorHandler(attributeId = 1)
	public void withColor() {}

	@ElementId("color state list")
	@ColorStateListHandler(attributeId = 1)
	public void withColorStateList() {}

	@ElementId("dimension")
	@DimensionHandler(attributeId = 1)
	public void withDimension() {}

	@ElementId("drawable")
	@DrawableHandler(attributeId = 1)
	public void withDrawable() {}

	@ElementId("enum constant")
	@EnumConstantHandler(attributeId = 1, enumClass = PlaceholderEnum.class)
	public void withEnumConstant() {}

	@ElementId("enum ordinal")
	@EnumOrdinalHandler(attributeId = 1)
	public void withEnumOrdinal() {}

	@ElementId("float")
	@FloatHandler(attributeId = 1)
	public void withFloat() {}

	@ElementId("fraction")
	@FractionHandler(attributeId = 1)
	public void withFraction() {}

	@ElementId("integer")
	@IntegerHandler(attributeId = 1)
	public void withInteger() {}

	@ElementId("string")
	@StringHandler(attributeId = 1)
	public void withString() {}

	@ElementId("text array")
	public void withTextArray() {}

	@ElementId("text")
	public void withText() {}

	public enum PlaceholderEnum {}
}